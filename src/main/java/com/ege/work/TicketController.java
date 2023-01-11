package com.ege.work;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public void calculate() {
        HashMap<String, HashMap<String, Double>> matrix = new HashMap<>();

        List<String> distinctTicketIds = ticketRepository.findDistinctTicketIds();

        for (String distinctTicket : distinctTicketIds) {
            List<Ticket> ticketList = ticketRepository.findByTicketId(distinctTicket);
            ArrayList<String> productIdList = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                productIdList.add(ticket.getProductId());
            }
            for (String productId : productIdList) {
                if (matrix.containsKey(productId)) {
                    for (String productId2 : productIdList) {
                        if (matrix.get(productId).containsKey(productId2)) {
                            matrix.get(productId).merge(productId2, 1.0, Double::sum);
                        } else {
                            matrix.get(productId).put(productId2, 1.0);
                        }
                    }
                } else {
                    matrix.put(productId, new HashMap<>());
                }
            }
        }

        for (String k : matrix.keySet()) {
            Double ticketSize = matrix.get(k).get(k);
            for (String k2 : matrix.get(k).keySet()) {
                if (k.equals(k2)) continue;

                Double val = matrix.get(k).get(k2) / ticketSize;
                matrix.get(k).put(k2, val);
            }
        }

        //System.err.println(matrix);

    }
}
