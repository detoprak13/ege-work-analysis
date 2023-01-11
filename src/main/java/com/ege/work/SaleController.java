package com.ege.work;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    public void deneme4() {
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

        CsvWriter.writeDataLineByLine("Deneme.csv", results);
    }
}
