package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import java.util.List;

public class MatchingEngine {

    public double matchEngineToOrder(double price, Order order, List<Order> oppositeList, List<Order> otherList){
        if(otherList == null ){
            throw new NullPointerException("OtherList is null, when the order should exist within the list.");
        }
        if(oppositeList == null || oppositeList.isEmpty()){
            return price;
        }

        int remainingQuantity = order.getQuantity();
        double orderPrice = order.getPrice();
        double priceExecution = price;

        if(order.getTypeOrder().equals("buy")) {
            while (remainingQuantity > 0 && !oppositeList.isEmpty()) {
                Order firstOrderInList = oppositeList.getFirst();

                if(orderPrice < firstOrderInList.getPrice()){
                    break;
                }

                if (remainingQuantity >= firstOrderInList.getQuantity()) {
                    priceExecution = firstOrderInList.getPrice();
                    remainingQuantity -= firstOrderInList.getQuantity();
                    oppositeList.removeFirst();

                } else {
                    priceExecution = firstOrderInList.getPrice();
                    firstOrderInList.setQuantity(firstOrderInList.getQuantity() - remainingQuantity);
                    remainingQuantity = 0;
                }
            }
        }
        else{
            while (remainingQuantity > 0 && !oppositeList.isEmpty()){
                Order firstsOrderInList = oppositeList.getFirst();

                if (orderPrice > firstsOrderInList.getPrice()){
                    break;
                }

                if (remainingQuantity >= firstsOrderInList.getQuantity()){
                    priceExecution = firstsOrderInList.getPrice();
                    remainingQuantity -= firstsOrderInList.getQuantity();
                    oppositeList.removeFirst();
                } else {
                    priceExecution = firstsOrderInList.getPrice();
                    firstsOrderInList.setQuantity(firstsOrderInList.getQuantity()-remainingQuantity);
                    remainingQuantity = 0;
                }

            }
        }
        if(remainingQuantity == 0){
            otherList.remove(order);
        } else {
            order.setQuantity(remainingQuantity);
        }
        return priceExecution;
    }

    public MatchResult matchEngineToOrderTaker(double price, Order order, List<Order> oppositeList) {
        MatchResult matchResult = new MatchResult();
        matchResult.setOrder(order);
        matchResult.setFullyExecuted(false);
        matchResult.setPriceExecution(price);

        if (oppositeList == null || oppositeList.isEmpty()) {
            return matchResult;
        }

        order.setPrice(oppositeList.getFirst().getPrice());
        int remainingQuantity = order.getQuantity();
        double priceExecution = price;

        while (!oppositeList.isEmpty() && remainingQuantity > 0) {
            Order firstOrderInList = oppositeList.getFirst();

            if(order.getTypeOrder().equals("buy") && order.getPrice() < oppositeList.getFirst().getPrice()){
                return matchResult;
            }
            if(order.getTypeOrder().equals("sell") && order.getPrice() > oppositeList.getFirst().getPrice()){
                return matchResult;
            }

            if (remainingQuantity >= firstOrderInList.getQuantity()) {
                matchResult.setPriceExecution(firstOrderInList.getPrice());
                remainingQuantity -= firstOrderInList.getQuantity();
                oppositeList.removeFirst();
            } else {
                firstOrderInList.setQuantity(firstOrderInList.getQuantity() - remainingQuantity);
                matchResult.setPriceExecution(firstOrderInList.getPrice());
                matchResult.setOrder(null);
                matchResult.setFullyExecuted(true);
                return matchResult;
            }
        }

        order.setQuantity(remainingQuantity);
        matchResult.setOrder(order);
        matchResult.setFullyExecuted(false);

        return matchResult;
    }
}
