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

    public double matchEngineToOrderTaker(double price, Order order, List<Order> oppositeList){
        if(oppositeList == null || oppositeList.isEmpty()){
            return price;
        }

        int orderNum = 0;
        boolean hasMatch = true;

        while (!hasMatch){

            Order orderInList = oppositeList.get(orderNum);

            if(order.getQuantity() > orderInList.getQuantity()){
                order.setQuantity(order.getQuantity()-orderInList.getQuantity());
                oppositeList.remove(orderNum);
            }
            else if(order.getQuantity() == orderInList.getQuantity()){
                oppositeList.remove(orderNum);
                return orderInList.getPrice().getValue();
            }
            else if (order.getQuantity() < orderInList.getQuantity()){
                orderInList.setQuantity(orderInList.getQuantity()-order.getQuantity());
                return orderInList.getPrice().getValue();
            }

            orderNum++;
        }
    }

}
