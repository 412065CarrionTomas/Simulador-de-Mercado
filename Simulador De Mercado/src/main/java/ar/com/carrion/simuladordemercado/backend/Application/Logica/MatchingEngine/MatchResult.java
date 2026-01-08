package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;


import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResult {
    private Order order;
    private double priceExecution;
    private boolean fullyExecuted;
}
