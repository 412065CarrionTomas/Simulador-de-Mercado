package ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RandomAlgorithm {

    public Order executeRandom(Long newIdOrder, double price) {
        boolean isMaker = Math.random() < 0.5;
        boolean isBuyer = Math.random() < 0.5;

        String typeOrder = isBuyer ? "buy" : "sell";

        return isMaker
                ? createMakerOrder(newIdOrder ,typeOrder, price)
                : createTakerOrder(newIdOrder ,typeOrder);
    }

    private Order createMakerOrder(Long idOrder,String typeOrder, double price) {
        Order orderMaker = new Order();
        orderMaker.setId(idOrder);
        orderMaker.setTypeOrder(typeOrder);
        orderMaker.setPrice(randomNewPrice(price));
        orderMaker.setQuantity(randomQuantity());
        orderMaker.setTime(LocalDateTime.now());
        return orderMaker;
    }

    private Order createTakerOrder(Long idOrder, String typeOrder) {
        Order orderTaker = new Order();
        orderTaker.setId(idOrder);
        orderTaker.setTypeOrder(typeOrder);
        orderTaker.setPrice(0.00);
        orderTaker.setQuantity(randomQuantity());
        orderTaker.setTime(LocalDateTime.now());
        return orderTaker;
    }

    private double randomNewPrice(double price) {
        double percentage = (Math.random() * 10) - 5;
        double adjusted = price * (1 + percentage / 100);
        return Math.round(adjusted * 1000.0) / 1000.0;
    }

    private int randomQuantity() {
        return (int) (Math.random() * 5) + 1;
    }
}