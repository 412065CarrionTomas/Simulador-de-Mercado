package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;


import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.lang.reflect.Array;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResult {
    private Order order;
    private Map<Order,String> oppositeOrders = new LinkedHashMap<>();
    private double priceExecution;
    private boolean fullyExecuted;
    private int quantityFilled;
    private int remainingQuantity; // posible eliminacion
}
