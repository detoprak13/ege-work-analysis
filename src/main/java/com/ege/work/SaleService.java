package com.ege.work;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale findById(SalePK id) {
        Optional<Sale> optionalSale = saleRepository.findById(id);

        if (optionalSale.isPresent()) {
            return optionalSale.get();
        } else {
            throw new RuntimeException("YOH  OYLE BISI");
        }
    }

    public List<Sale> findByIkameGrup(String ikameGrup) {
        return saleRepository.findByIkameGrup(ikameGrup);
    }

    public List<String> findCustsForIkameGrup(String ikameGrup) {
        return saleRepository.findCustsForIkameGrup(ikameGrup);
    }

    public List<Sale> findByIkameGrupAndCustCardNum(String ikameGrup, String custCardNum) {
        return saleRepository.findByIkameGrupAndCustCardNum(ikameGrup, custCardNum);
    }

    public List<String> findUniqueIkameGrups() {
        return saleRepository.findUniqueIkameGrups();
    }

    public void truncate() {
        saleRepository.truncate();
    }

    public Sale create(Sale sale) {
        return saleRepository.save(sale);
    }

    public void create(List<Sale> sales) {
        saleRepository.saveAll(sales);
    }
}
