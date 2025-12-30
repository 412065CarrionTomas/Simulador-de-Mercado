package ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Random;

public class RandomAlgorithm {

    private final Random random = new Random();


    public Order executeRandom(double price) {
        boolean isMaker = random.nextBoolean();
        boolean isBuyer = random.nextBoolean();

        String typeOrder = isBuyer ? "buy" : "sell";

        return isMaker
                ? createMakerOrder(typeOrder, price, 1)
                : createTakerOrder(typeOrder);
    }

    private Order createMakerOrder(String typeOrder, double price, int quantity) {
        Order orderMaker = new Order();
        orderMaker.setTypeOrder(typeOrder);

        Price priceObj = new Price();
        priceObj.setValue(randomNewPrice(price));
        orderMaker.setPrice(priceObj);

        orderMaker.setQuantity(quantity);
        orderMaker.setTime(LocalDateTime.now());
        return orderMaker;
    }

    private Order createTakerOrder(String typeOrder) {
        Order orderTaker = new Order();
        orderTaker.setTypeOrder(typeOrder);
        orderTaker.setPrice(null);
        orderTaker.setQuantity(1);
        orderTaker.setTime(LocalDateTime.now());
        return orderTaker;
    }

    private double randomNewPrice(double price) {
        double percentage = (random.nextDouble() * 10) - 5;
        double adjusted = price * (1 + percentage / 100);
        return Math.round(adjusted * 1000.0) / 1000.0;
    }
}