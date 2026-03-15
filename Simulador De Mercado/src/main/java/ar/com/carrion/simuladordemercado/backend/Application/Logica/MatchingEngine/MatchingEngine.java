package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchingEngine {

    public MatchResult matchEngineToOrder(Order order, List<Order> oppositeList, List<Order> otherList){
        MatchResult matchResult = new MatchResult();

        if(otherList == null ){
            throw new NullPointerException("OtherList is null, when the Order should exist within the list.");
        }
        if(oppositeList == null || oppositeList.isEmpty()){
            return matchResult;
        }

        executeEngine(matchResult,order,oppositeList);

        return matchResult;
    }


    public MatchResult matchEngineToOrderTaker(double priceCandle, Order order, List<Order> oppositeList) {
        MatchResult matchResult = getMatchResult(order);

        if (oppositeList == null || oppositeList.isEmpty()) {
            order.setPrice(priceCandle);
             return matchResult;
        }

        order.setPrice(oppositeList.getFirst().getPrice());
        /* como hay elementos en la lista opositora, al ser taker
         * debe tomar los precios de la primera orden opositora. Como por metodo solo va a existir una unica mainOrder
         * no debo actualizar el precio por cada vuelta.
         * */

        executeEngine(matchResult,order,oppositeList);

        return matchResult;
    }

    private MatchResult executeEngine(MatchResult matchResult ,Order order, List<Order> oppositeList){

        int orderRemainingQty = order.getQuantity();
        /* en esta variable quiero guardar constantemente los nuevos cambios de Qty de la mainOrder
         * */

        int initialMainOrderQty = order.getQuantity();
        /* en esta variable quiero guardar con que cantidad comenzo esta vuelta While.
         * si dio una vuelta completa sin ser completamente ejecutada, debera almacenar el valor resultante de orderRemaininQty y preservarlo
         * toda la proxima vuelta... asi sucesivamente hasta ser completamente ejecutada la mainOrder.
         * */

        while (!oppositeList.isEmpty() && orderRemainingQty > 0) {
            Order firstOrderInOppositeList = oppositeList.getFirst();

            if(order.getTypeOrder().equals("buy") && order.getPrice() < firstOrderInOppositeList.getPrice()){break;}
            if(order.getTypeOrder().equals("sell") && order.getPrice() > firstOrderInOppositeList.getPrice()){break;}

            double priceExecution = calculateExecutionPrice(order,firstOrderInOppositeList);
            /*si supero el if, quiere decir que si o si existe match posible. Previo a la logica
             * de modificar las ordenes, calculo el precio de eejecucion
             * */
            matchResult.setLastTradeExecute(priceExecution);

            int initialOppositeOrderQty = firstOrderInOppositeList.getQuantity();

            if (orderRemainingQty >= firstOrderInOppositeList.getQuantity()) {

                orderRemainingQty -= firstOrderInOppositeList.getQuantity();

                matchResult.getOppositeOrdersModified().add(getOrderResult(
                        firstOrderInOppositeList,initialOppositeOrderQty,0,priceExecution,order.getId()
                ));

                matchResult.getMainOrdersModified().add(getOrderResult(
                        order, initialMainOrderQty,orderRemainingQty,priceExecution,firstOrderInOppositeList.getId()
                ));
                /* como se puede ver. Le paso el initialMainOrderQty que habia inicializado por fuera de ciclo while.
                 * Es decir, contiene la cantidad con la que comenzo esta vuelta.
                 * Luego la seteo a como fue su resultado de esta vuelta.
                 * Entonces, esta se mantiene igual, para agregarla con ese valor. Luego la modifico.
                 * */
                initialMainOrderQty = orderRemainingQty;

                oppositeList.removeFirst();
            } else {
                firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity() - orderRemainingQty);

                orderRemainingQty = 0;

                matchResult.getOppositeOrdersModified().add(getOrderResult(
                        firstOrderInOppositeList,initialOppositeOrderQty,firstOrderInOppositeList.getQuantity(),priceExecution,order.getId()
                ));

                matchResult.getMainOrdersModified().add(getOrderResult(
                        order,initialMainOrderQty,0,priceExecution,firstOrderInOppositeList.getId()
                ));
            }
        }

        matchResult.getMainOrder().setQuantity(orderRemainingQty);

        return matchResult;
    }

    private MatchResult getMatchResult(Order order) {
        MatchResult matchResult = new MatchResult();
        matchResult.setLastTradeExecute(0);
        matchResult.setOppositeOrdersModified(new ArrayList<>());
        matchResult.setMainOrdersModified(new ArrayList<>());
        matchResult.setMainOrder(order);
        return matchResult;
    }

    private OrderResult getOrderResult(Order order, int initialQty, int remainingQty, double priceExecution, Long idOrderOpposite){
        return new OrderResult(order.getId(),order.getPrice(),initialQty,priceExecution,
                remainingQty==0,initialQty-remainingQty,remainingQty,idOrderOpposite);
    }

    private static double calculateExecutionPrice(Order order, Order firstOrderInOppositeList) {
        double priceExecution;
        priceExecution = (order.getTypeOrder().equals("buy"))
                ? firstOrderInOppositeList.getPrice()
                : order.getPrice();
        return priceExecution;
    }
}
//
//
//int qtyOrderStart = order.getQuantity();
//int orderRemainingQty = order.getQuantity();
//double orderPrice = order.getPrice();
//
//        while (orderRemainingQty > 0 && !oppositeList.isEmpty()){
//Order firstOrderInOppositeList = oppositeList.getFirst();
//
//            if(order.getTypeOrder().equals("buy") && orderPrice < firstOrderInOppositeList.getPrice()){
//        break;
//        }
//        if(order.getTypeOrder().equals("sell") && orderPrice > firstOrderInOppositeList.getPrice()){
//        break;
//        }
//
//        if(orderRemainingQty >= firstOrderInOppositeList.getQuantity()){
//        matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInOppositeList));
//orderRemainingQty -=firstOrderInOppositeList.getQuantity();
//                oppositeList.removeFirst();
//                matchResult.getOrdersWhitOppositesIdOrders().put(firstOrderInOppositeList, order.getId());
//        matchResult.getOrdersWhitOppositesIdOrders().put(matchResult.getOrder(), firstOrderInOppositeList.getId());
//        } else {
//        matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInOppositeList));
//        firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity()- orderRemainingQty);
//orderRemainingQty = 0;
//        matchResult.getOrdersWhitOppositesIdOrders().put(firstOrderInOppositeList, order.getId());
//        matchResult.getOrdersWhitOppositesIdOrders().put(matchResult.getOrder(), firstOrderInOppositeList.getId());
//        }
//        }
//
//        if (orderRemainingQty == 0){
//        otherList.remove(order);
//            matchResult.setFullyExecuted(true);
//            matchResult.getOrder().setQuantity(0);
//        }else {
//                matchResult.getOrder().setQuantity(orderRemainingQty);
//        }
//
//                matchResult.setQuantityFilled(qtyOrderStart-orderRemainingQty);
//        matchResult.setRemainingQuantity(orderRemainingQty);
