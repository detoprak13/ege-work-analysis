package com.ege.work;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void writeDataLineByLine(String filePath, List<Result> results)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "ikame_grup", "esas_artikel_no", "ikame_artikel_no", "adet_degisimi" };
            writer.writeNext(header);

            for (Result result : results) {
                for (SaleInnerDto saleInnerDto : result.getDto()) {
                    for (SaleDto sale : saleInnerDto.getSales()) {
                        String[] line = {result.getIkameGrup(), saleInnerDto.getArtikelNo(),
                                sale.getArtikelNo(), sale.getToplamUrunAdedi().toString()};
                        writer.writeNext(line);
                    }
                }
            }
            // add data to csv
//            String[] data1 = { "Aman", "10", "620" };
//            writer.writeNext(data1);
//            String[] data2 = { "Suraj", "10", "630" };
//            writer.writeNext(data2);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
