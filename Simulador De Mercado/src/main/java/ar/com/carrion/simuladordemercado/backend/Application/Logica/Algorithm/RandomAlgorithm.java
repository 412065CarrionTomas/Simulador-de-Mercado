package ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import java.time.LocalDateTime;

public class RandomAlgorithm {

    public Order executeRandom(double price) {
        boolean isMaker = Math.random() < 0.5;
        boolean isBuyer = Math.random() < 0.5;

        String typeOrder = isBuyer ? "buy" : "sell";

        return isMaker
                ? createMakerOrder(typeOrder, price)
                : createTakerOrder(typeOrder);
    }

    private Order createMakerOrder(String typeOrder, double price) {
        Order orderMaker = new Order();
        orderMaker.setTypeOrder(typeOrder);
        orderMaker.setPrice(randomNewPrice(price));
        orderMaker.setQuantity(randomQuantity());
        orderMaker.setTime(LocalDateTime.now());
        return orderMaker;
    }

    private Order createTakerOrder(String typeOrder) {
        Order orderTaker = new Order();
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