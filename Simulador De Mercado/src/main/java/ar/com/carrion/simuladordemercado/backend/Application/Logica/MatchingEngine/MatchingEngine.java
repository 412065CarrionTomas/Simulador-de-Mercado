package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class MatchingEngine {

    public MatchResult matchEngineToOrder(double priceCandle, Order order, List<Order> oppositeList, List<Order> otherList){
        MatchResult matchResult = getMatchResult(priceCandle,order);

        if(otherList == null ){
            throw new NullPointerException("OtherList is null, when the Order should exist within the list.");
        }
        if(oppositeList == null || oppositeList.isEmpty()){
            return matchResult;
        }

        int qtyOrderStart = order.getQuantity();
        int orderRemainigQty = order.getQuantity();
        double orderPrice = order.getPrice();

        while (orderRemainigQty > 0 && !oppositeList.isEmpty()){
            Order firstOrderInOppositeList = oppositeList.getFirst();

            if(order.getTypeOrder().equals("buy") && orderPrice < firstOrderInOppositeList.getPrice()){
                break;
            }
            if(order.getTypeOrder().equals("sell") && orderPrice > firstOrderInOppositeList.getPrice()){
                break;
            }

            if(orderRemainigQty >= firstOrderInOppositeList.getQuantity()){
                matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInOppositeList));
                matchResult.getOrders().put(firstOrderInOppositeList,"FILLED");
                orderRemainigQty -=firstOrderInOppositeList.getQuantity();
                oppositeList.removeFirst();
            } else {
                matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInOppositeList));
                firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity()- orderRemainigQty);
                matchResult.getOrders().put(firstOrderInOppositeList,"PARTIALLY_FILLED");
                orderRemainigQty = 0;
            }
        }

        if (orderRemainigQty == 0){
            otherList.remove(order);
            matchResult.setFullyExecuted(true);
            matchResult.getOrder().setQuantity(0);
            matchResult.getOrders().put(matchResult.getOrder(),"FILLED");
        }else {
            matchResult.getOrder().setQuantity(orderRemainigQty);
            matchResult.getOrders().put(matchResult.getOrder(),"PARTIALLY_FILLED");
        }

        matchResult.setQuantityFilled(qtyOrderStart-orderRemainigQty);
        matchResult.setRemainingQuantity(orderRemainigQty);

        return matchResult;
    }


    public MatchResult matchEngineToOrderTaker(double priceCandle, Order order, List<Order> oppositeList) {
        MatchResult matchResult = getMatchResult(priceCandle, order);

        if (oppositeList == null || oppositeList.isEmpty()) {
             return matchResult;
        }

        final int qtyOrderStart = order.getQuantity();
        int orderRemainigQty = order.getQuantity();
        double orderPrice = order.getPrice();

        matchResult.getOrder().setPrice(oppositeList.getFirst().getPrice());

        while (!oppositeList.isEmpty() && orderRemainigQty > 0) {
            Order firstOrderInOppositeList = oppositeList.getFirst();

            if(order.getTypeOrder().equals("buy") && orderPrice < oppositeList.getFirst().getPrice()){
                break;
            }
            if(order.getTypeOrder().equals("sell") && orderPrice > oppositeList.getFirst().getPrice()){
                break;
            }
            if (orderRemainigQty >= firstOrderInOppositeList.getQuantity()) {
                matchResult.setPriceExecution(calculateExecutionPrice(order, firstOrderInOppositeList));
                matchResult.getOrders().put(firstOrderInOppositeList,"FILLED");
                orderRemainigQty -= firstOrderInOppositeList.getQuantity();
                oppositeList.removeFirst();
            } else {
                firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity() - orderRemainigQty);
                matchResult.setPriceExecution(calculateExecutionPrice(order, firstOrderInOppositeList));
                matchResult.getOrders().put(firstOrderInOppositeList,"PARTIALLY_FILLED");
                orderRemainigQty = 0;
            }
        }

        if (orderRemainigQty == 0) {
            matchResult.setFullyExecuted(true);
            matchResult.getOrder().setQuantity(0);
            matchResult.getOrders().put(matchResult.getOrder(),"FILLED");
        } else {
            matchResult.getOrder().setQuantity(orderRemainigQty);
            matchResult.getOrders().put(matchResult.getOrder(),"PARTIALLY_FILLED");
        }

        matchResult.setQuantityFilled(qtyOrderStart-orderRemainigQty);
        matchResult.setRemainingQuantity(orderRemainigQty);

        return matchResult;
    }

    private static double calculateExecutionPrice(Order order, Order firstOrderInOppositeList) {
        double priceExecution;
        priceExecution = (order.getTypeOrder().equals("buy"))
                ? firstOrderInOppositeList.getPrice()
                : order.getPrice();
        return priceExecution;
    }

    private static @NonNull MatchResult getMatchResult(double priceCandle, Order order) {
        MatchResult matchResult = new MatchResult();
        matchResult.setOrder(new Order(order));
        matchResult.getOrder().setPrice(priceCandle);
        matchResult.setOrders(new LinkedHashMap<>());
        matchResult.setFullyExecuted(false);
        matchResult.setPriceExecution(0);
        matchResult.setQuantityFilled(0);
        matchResult.setRemainingQuantity(order.getQuantity());
        return matchResult;
    }
}