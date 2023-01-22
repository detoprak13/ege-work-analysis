package com.ege.work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface SaleRepository extends JpaRepository<Sale, SalePK> {
    @Query("SELECT distinct s.ikameGrup from Sale s")
    List<String> findUniqueIkameGrups();

    @Query("SELECT distinct s.custCardNum from Sale s where s.ikameGrup = :ikameGrup")
    List<String> findCustsForIkameGrup(String ikameGrup);

    List<Sale> findByIkameGrup(String ikameGrup);

    List<Sale> findByIkameGrupAndCustCardNum(String ikameGrup, String custCardNum);

    @Modifying
    @Query(
            value = "truncate table table_name_2",
            nativeQuery = true
    )
    void truncate();
}
