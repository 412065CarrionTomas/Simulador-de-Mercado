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
        double bestPriceInList = oppositeList.getFirst().getPrice().getValue();

        if (order.getPrice().getValue() >= bestPriceInList && order.getTypeOrder().equals("buy")){
            oppositeList.removeFirst();
            otherList.remove(order);
            return bestPriceInList;
        }
        if(order.getPrice().getValue() <= bestPriceInList && order.getTypeOrder().equals("sell")){
            oppositeList.removeFirst();
            otherList.remove(order);
            return bestPriceInList;
        }
        return price;
    }

    public double matchEngineToOrderTaker(double price,List<Order> orderList){
        if(orderList == null || orderList.isEmpty()){
            return price;
        }
        double bestPriceInList = orderList.getFirst().getPrice().getValue();

        orderList.removeFirst();

        return bestPriceInList;
    }

}
