package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchingEngine {

    public double matchEngineToOrder(double price, Order order, List<Order> oppositeList, List<Order> otherList){
        MatchResult matchResult = getMatchResult(price,order);

        if(otherList == null ){
            throw new NullPointerException("OtherList is null, when the Order should exist within the list.");
        }
        if(oppositeList == null || oppositeList.isEmpty()){
            return price;
        }

        int remainingQuantity = order.getQuantity();
        double orderPrice = order.getPrice();
        double priceExecution = price; // siempre tiene que ser igual al trade ultimo. El precio del trade ulitmo lo decide la venta
                                        // si pago con 200 algo que vale 100, se me devuelve el vuelto. El trade es de 100.

        while (remainingQuantity > 0 && !oppositeList.isEmpty()){
            Order firstOrderInOppositeList = oppositeList.getFirst();

            if(order.getTypeOrder().equals("buy") && orderPrice < oppositeList.getFirst().getPrice()){
                return price;
            }
            if(order.getTypeOrder().equals("sell") && orderPrice > oppositeList.getFirst().getPrice()){
                return price;
            }

            if(remainingQuantity >= firstOrderInOppositeList.getQuantity()){
                priceExecution = calculateExecutionPrice(order, firstOrderInOppositeList, orderPrice);
                remainingQuantity -=firstOrderInOppositeList.getQuantity();
                oppositeList.removeFirst();
            } else {
                priceExecution = calculateExecutionPrice(order, firstOrderInOppositeList, orderPrice);
                firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity()-remainingQuantity);
                remainingQuantity = 0;
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
        MatchResult matchResult = getMatchResult(price, order);
        int qtyOrderStart = order.getQuantity();

        if (oppositeList == null || oppositeList.isEmpty()) {
             return matchResult;
        }

        order.setPrice(oppositeList.getFirst().getPrice());

        while (!oppositeList.isEmpty() && order.getQuantity() > 0) {
            Order firstOrderInList = oppositeList.getFirst();

            if(order.getTypeOrder().equals("buy") && order.getPrice() < oppositeList.getFirst().getPrice()){
                return matchResult;
            }
            if(order.getTypeOrder().equals("sell") && order.getPrice() > oppositeList.getFirst().getPrice()){
                return matchResult;
            }

            if (order.getQuantity() >= firstOrderInList.getQuantity()) {
                /* MODIFIQUE
                *  agregue matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInList,order.getPrice()));
                * */
                matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInList,order.getPrice()));
                //MODIFIQUE ESTA LINEA 17/2/2026 matchResult.getOrder().getQuantity() ANTES ERA order.set quantity
                matchResult.getOrder().setQuantity(order.getQuantity()-firstOrderInList.getQuantity());
                //AGREGE ESTA LINEA 19/2/2026
                order.setQuantity(order.getQuantity()-firstOrderInList.getQuantity());

                oppositeList.removeFirst();
            } else {
                firstOrderInList.setQuantity(firstOrderInList.getQuantity() - order.getQuantity());
                /* MODIFIQUE
                 *  agregue matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInList,order.getPrice()));
                 * */
                matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInList,order.getPrice()));
                matchResult.setOrder(null);
                matchResult.setFullyExecuted(true);
                return matchResult;
            }
        }

        matchResult.setQuantityFilled(qtyOrderStart-order.getQuantity());
        matchResult.setRemainingQuantity(order.getQuantity());

        if(order.getQuantity() == 0){
            matchResult.setOrder(null);
            matchResult.setFullyExecuted(true);
        }
        if(oppositeList.isEmpty()){
            return matchResult;
        }
        return matchResult;
    }

    private static double calculateExecutionPrice(Order order, Order firstOrderInOppositeList, double orderPrice) {
        double priceExecution;
        priceExecution = (order.getTypeOrder().equals("buy"))
                ? firstOrderInOppositeList.getPrice()
                : orderPrice;
        return priceExecution;
    }

    private static @NonNull MatchResult getMatchResult(double price, Order order) {
        MatchResult matchResult = new MatchResult();
        order.setPrice(price);
        matchResult.setOrder(order);
        matchResult.setFullyExecuted(false);
        matchResult.setPriceExecution(price);
        matchResult.setQuantityFilled(0);
        matchResult.setRemainingQuantity(order.getQuantity());
        return matchResult;
    }
}


//
//
//        if(order.getTypeOrder().equals("buy")) {
//        while (remainingQuantity > 0 && !oppositeList.isEmpty()) {
//Order firstOrderInOppositeList = oppositeList.getFirst();
//
//                if(orderPrice < firstOrderInOppositeList.getPrice()){
//        break;
//        }
//
//        if (remainingQuantity >= firstOrderInOppositeList.getQuantity()) {
//priceExecution = firstOrderInOppositeList.getPrice();
//remainingQuantity -= firstOrderInOppositeList.getQuantity();
//                    oppositeList.removeFirst();
//
//                } else {
//priceExecution = firstOrderInOppositeList.getPrice();
//                    firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity() - remainingQuantity);
//remainingQuantity = 0;
//        }
//        }
//        }
//        else{
//        while (remainingQuantity > 0 && !oppositeList.isEmpty()){
//Order firstsOrderInOppositeList = oppositeList.getFirst();
//
//                if (orderPrice > firstsOrderInOppositeList.getPrice()){
//        break;
//        }
//
//        if (remainingQuantity >= firstsOrderInOppositeList.getQuantity()){
//MODIFIQUE ESTA LINEA 19/2/2026 antes era firstsOrderInOppositeList.getPrice();
//priceExecution = orderPrice;
//remainingQuantity -= firstsOrderInOppositeList.getQuantity();
//                    oppositeList.removeFirst();
//                } else {
//MODIFIQUE ESTA LINEA 19/2/2026 antes era firstsOrderInOppositeList.getPrice();
//priceExecution = orderPrice;
//                    firstsOrderInOppositeList.setQuantity(firstsOrderInOppositeList.getQuantity()-remainingQuantity);
//remainingQuantity = 0;
//        }
//
//        }
//        }
//
//