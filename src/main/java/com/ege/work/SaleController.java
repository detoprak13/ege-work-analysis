package com.ege.work;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.RoundingMode.HALF_UP;

@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping()
    public Sale findById(@RequestParam String custCardNum,
                         @RequestParam String artikelNo) {
        return saleService.findById(new SalePK(custCardNum, artikelNo));
    }
    //1_Yas_Alti_Mamalar---L

    @GetMapping("/islem")
    public ArrayList<SaleInnerDto> deneme(@RequestParam String ikameGrup, @RequestParam String custCardNum) {
        List<Sale> sales = saleService.findByIkameGrupAndCustCardNum(ikameGrup, custCardNum);
// 6221510683336604

        ArrayList<SaleInnerDto> result = new ArrayList<>();
        BigDecimal totalCount = BigDecimal.ZERO; //.valueOf(0)
        BigDecimal toBeRemovedCount = BigDecimal.ZERO; //bunu ekledik

        for (Sale sale : sales) {
            totalCount = totalCount.add(BigDecimal.valueOf(sale.getToplamUrunAdedi()));
            if (sale.getEsasUrunFlag() == 0) continue;// yeni
            toBeRemovedCount = toBeRemovedCount.add(BigDecimal.valueOf(sale.getToplamUrunAdedi())); // yeni
        }

        BigDecimal removedCount = totalCount.subtract(toBeRemovedCount);// yeni BigDecimal

        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getEsasUrunFlag() == 0) continue; // yeni satır toplam için

            //  BigDecimal removedCount = totalCount.subtract(BigDecimal.valueOf(sales.get(i).getToplamUrunAdedi())); // bunu geri koy

            SaleInnerDto saleInnerDto = new SaleInnerDto();
            saleInnerDto.setArtikelNo(sales.get(i).getArtikelNo());
            ArrayList<SaleDto> subList = new ArrayList<>();
            saleInnerDto.setSales(subList);
            result.add(saleInnerDto);

            if (removedCount.equals(BigDecimal.ZERO)) continue;
            try {

                BigDecimal unit = BigDecimal.valueOf(sales.get(i).getToplamUrunAdedi()).divide(removedCount, 8, HALF_UP);
                System.err.println("");
                for (int j = 0; j < sales.size(); j++) {
                    if (i == j) continue;
                    if (sales.get(j).getEsasUrunFlag() == 1) continue; // yeni satır toplam için
                    SaleDto dto = new SaleDto();
                    subList.add(dto);


                    dto.setArtikelNo(sales.get(j).getArtikelNo());
                    dto.setToplamUrunAdedi(unit.multiply(BigDecimal.valueOf(sales.get(j).getToplamUrunAdedi())).doubleValue());
                }
            } catch (Exception e) {
                System.err.println(removedCount);
                e.printStackTrace();
            }
        }

        return result;

    }

    @GetMapping("/islem2")
    public ArrayList<SalePerCust> deneme2(@RequestParam String ikameGrup) {
        List<String> custs = saleService.findCustsForIkameGrup(ikameGrup);

        ArrayList<SalePerCust> result = new ArrayList<>();

        custs.forEach(cust -> {
            SalePerCust a = new SalePerCust();
            a.setCustCardNum(cust);
            a.setSaleResults(deneme(ikameGrup, cust));
            result.add(a);
        });

        return result;
    }

    @GetMapping("/islem3")
    public ArrayList<SaleIkameGrupDto> deneme3() {
        List<String> ikameGrups = saleService.findUniqueIkameGrups();

        ArrayList<SaleIkameGrupDto> result = new ArrayList<>();
        int count = 0;
        for (String ikameGrup : ikameGrups) {
//            if (count == 5) break;
            SaleIkameGrupDto a = new SaleIkameGrupDto();
            a.setIkameGrup(ikameGrup);
            a.setSalePerCusts(deneme2(ikameGrup));
            result.add(a);
            count++;
        }
        return result;
    }

    @GetMapping("/islem4")
    public void deneme4(@RequestParam(required = false) String resultFileName) {
        if (resultFileName == null || resultFileName.isEmpty() || resultFileName.isBlank())
            resultFileName = "output" + LocalDateTime.now();
        List<Result> results = new ArrayList<>();
        ArrayList<SaleIkameGrupDto> deneme3 = deneme3();
        for (SaleIkameGrupDto saleIkameGrupDto : deneme3) {
            Result result = new Result();
            result.setIkameGrup(saleIkameGrupDto.getIkameGrup());
            results.add(result);

            for (SalePerCust salePerCust : saleIkameGrupDto.getSalePerCusts()) {
                for (SaleInnerDto saleResult : salePerCust.getSaleResults()) {
                    SaleInnerDto saleInnerDto;
                    if (result.getDto().contains(saleResult)) {
                        saleInnerDto = result.getDto().get(result.getDto().indexOf(saleResult));
                    } else {
                        saleInnerDto = new SaleInnerDto();
                        saleInnerDto.setArtikelNo(saleResult.getArtikelNo());
                        result.getDto().add(saleInnerDto);
                    }

                    for (SaleDto sale : saleResult.getSales()) {
                        if (saleInnerDto.getSales().contains(sale)) {
                            SaleDto currSale = saleInnerDto.getSales().get(saleInnerDto.getSales().indexOf(sale));
                            currSale.setToplamUrunAdedi(currSale.getToplamUrunAdedi() + sale.getToplamUrunAdedi());
                        } else {
                            saleInnerDto.getSales().add(sale);
                        }
                    }
                }
            }
        }

        CsvWriter.writeDataLineByLine(resultFileName + ".csv", results);
    }

    @GetMapping("/truncate")
    public void truncate() {
        saleService.truncate();
    }

    @PostMapping("/upload")
    public void upload(@RequestParam String path) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(path));
            CSVFormat csvFormat = CSVFormat.Builder
                    .create()
                    .setSkipHeaderRecord(true)
                    .build();
            CSVParser csvParser = new CSVParser(fileReader, csvFormat);

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            ArrayList<Sale> sales = new ArrayList<>();

            boolean isFirstLine = true;
            int batchCount = 0;
            for (CSVRecord csvRecord : csvRecords) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                Sale sale = new Sale(csvRecord.get(0),
                        csvRecord.get(1),
                        csvRecord.get(2),
                        Integer.parseInt(csvRecord.get(3)),
                        csvRecord.get(4),
                        Double.parseDouble(csvRecord.get(5)));

                sales.add(sale);
                batchCount++;
                if (batchCount == 1000) {
                    saleService.create(sales);
                    sales = new ArrayList<>();
                    batchCount = 0;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
