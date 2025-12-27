package ar.com.carrion.simuladordemercado.backend.Algorithm;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;

import java.util.Random;

public class RandomAlgorithm implements IAlgorithStrategy {

    private final Random random = new Random();

    @Override
    public Order execute(double price) {
        boolean isMaker = random.nextBoolean();
        boolean isBuyer = random.nextBoolean();

        String typeOrder = isBuyer ? "buy" : "sell";

        return isMaker
                ? createMakerOrder(typeOrder, price, 1)
                : createTakerOrder(typeOrder);
    }

    private Order createMakerOrder(String typeOrder, double price, int quantity) {
        Order order = new Order();
        order.setTypeOrder(typeOrder);

        Price priceObj = new Price();
        priceObj.setPrice(randomNewPrice(price));
        order.setPrice(priceObj);

        order.setQuantity(quantity);
        return order;
    }

    private Order createTakerOrder(String typeOrder) {
        Order orderTake = new Order();
        orderTake.setTypeOrder(typeOrder);
        orderTake.setPrice(null);
        orderTake.setQuantity(0);
        return orderTake;
    }

    private double randomNewPrice(double price) {
        double percentage = (random.nextDouble() * 10) - 5;
        double adjusted = price * (1 + percentage / 100);
        return Math.round(adjusted * 1000.0) / 1000.0;
    }
}