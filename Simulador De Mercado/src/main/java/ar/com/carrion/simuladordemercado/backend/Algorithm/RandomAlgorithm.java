package ar.com.carrion.simuladordemercado.backend.Algorithm;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Services.OrderBookService.OrderBookService;

public class RandomAlgorithm {
    private final OrderBookService orderBookService;

    public RandomAlgorithm(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    public OrderBook random(double price, OrderBook orderBook){
        String typeOfRandom = randomType();
        Order order = createOrder(price,1);

        switch (typeOfRandom){
            case "MakerAndBuyer" -> orderBookService.addBuyOrder(order);
            case "MakerAndSeller" -> orderBookService.addSellOrder(order);
        }
    }

    private Order createOrder(double price, int quantity){
        Order order = new Order();
        order.setPrice(randomNewPrice(price));
        order.setQuantity(quantity);
        return order;
    }

    private String randomType(){
        int randomBit = Math.random() < 0.5 ? 0 : 1;
        boolean makerOrTaker = (randomBit == 1);
        boolean buyOrSell = (randomBit == 1);

        if(makerOrTaker == true && buyOrSell == true){
            return  "MakerAndBuyer";
        }
        if (makerOrTaker == true && buyOrSell == false) {
            return  "MakerAndSeller";
        }
        if (makerOrTaker == false && buyOrSell == true){
            return "TakerAndBuyer";
        }
        if(makerOrTaker == false && buyOrSell == false){
            return "TakerAndSeller";
        }
        return "error";
    }

    private double randomNewPrice(double price) {
        double percentage = (Math.random() * 10) - 5;
        double adjusted = price * (1 + percentage / 100);
        double factor = 1000.0;
        return ((long) (adjusted * factor)) / factor;
    }

}

//
//if(typeOfRandom.equals("MakerAndBuyer")){
//Order order = new Order();
//            order.setPrice(randomNewPrice(price));
//        order.setQuantity(1);
//            orderBook.getAsks().add(order);
//            return orderBook;
//        }
//                if(typeOfRandom.equals("MakerAndSeller")){
//Order order = new Order();
//            order.setPrice(randomNewPrice(price));
//        order.setQuantity(1);
//            orderBook.getBids().add(order);
//            return orderBook;
//        }
//
//                if(typeOfRandom.equals("TakerAndBuyer")){
//        return null;
//        }
//        return  null;