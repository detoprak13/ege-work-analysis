package com.ege.work;

import com.ege.work.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("select distinct t.ticketId from Ticket t")
    List<String> findDistinctTicketIds();

    List<Ticket> findByTicketId(String ticketId);
}
