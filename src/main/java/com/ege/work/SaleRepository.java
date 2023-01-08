package com.ege.work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, SalePK> {
    @Query("SELECT distinct s.ikameGrup from Sale s")
    List<String> findUniqueIkameGrups();

    @Query("SELECT distinct s.custCardNum from Sale s where s.ikameGrup = :ikameGrup")
    List<String> findCustsForIkameGrup(String ikameGrup);

    List<Sale> findByIkameGrup(String ikameGrup);

    List<Sale> findByIkameGrupAndCustCardNum(String ikameGrup, String custCardNum);
}
