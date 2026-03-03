package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MatchingEngine {

    /* ADAPTACION para retornar matchResult en lugar de double
    * primero instanciar el matchResult por defecto con getMatchResult()
    * aspecto mas importante a tener en cuenta: matchResult.priceExecution.*/
    public MatchResult matchEngineToOrder(double priceCandle, Order order, List<Order> oppositeList, List<Order> otherList){
        MatchResult matchResult = getMatchResult(priceCandle,order);

        if(otherList == null ){
            throw new NullPointerException("OtherList is null, when the Order should exist within the list.");
        }
        if(oppositeList == null || oppositeList.isEmpty()){
            return matchResult;
        }

        int qtyStart = order.getQuantity();
        int orderRemainigQty = order.getQuantity();
        double orderPrice = order.getPrice();
//        double priceExecution = priceCandle; // siempre tiene que ser igual al trade ultimo. El precio del trade ulitmo lo decide la venta
                                        // si pago con 200 algo que vale 100, se me devuelve el vuelto. El trade es de 100.

        while (orderRemainigQty > 0 && !oppositeList.isEmpty()){
            Order firstOrderInOppositeList = oppositeList.getFirst();

            if(order.getTypeOrder().equals("buy") && orderPrice < oppositeList.getFirst().getPrice()){
                break; // PRIMERA modificacion, cambie return priceCandle; por break
            }
            if(order.getTypeOrder().equals("sell") && orderPrice > oppositeList.getFirst().getPrice()){
                break; // SEGUNDA modificacion, cambie return priceCandle; por break
            }

            if(orderRemainigQty >= firstOrderInOppositeList.getQuantity()){
                matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInOppositeList));
//                priceExecution = calculateExecutionPrice(order, firstOrderInOppositeList);
                orderRemainigQty -=firstOrderInOppositeList.getQuantity();

                /* Esta linea es innecesaria. Ya que dice lo mismo que orderRemainingQty
                * Ademas de que su ubicacion tambien es innecesaria, ya que se actualiza constantemente
                * puediendo hacerlo una sola vez, luego del while. La senalizo con (*2)
                * matchResult.getOrder().setQuantity(order.getQuantity()-firstOrderInOppositeList.getQuantity());*/
                /**/


                /* Elimino esta linea. Ya que matchResult tiene una copia de "order". Me conviene dejar la Order original
                * y en el matchResult la modificada.
                * order.setQuantity(order.getQuantity()- firstOrderInOppositeList.getQuantity());*/
                /**/

                oppositeList.removeFirst();
            } else {
                matchResult.setPriceExecution(calculateExecutionPrice(order,firstOrderInOppositeList));
//                priceExecution = calculateExecutionPrice(order, firstOrderInOppositeList);
                firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity()- orderRemainigQty);
                otherList.remove(order); // (*1)
                orderRemainigQty = 0;
            }
        }

        matchResult.getOrder().setQuantity(orderRemainigQty); //(*2)

        /* Elimino este bloque, ya que lo puedo resumir dentro del mismo while.
        * Las acciones de este bloque van a estar senalizadas con (*1)
        * if(orderRemainigQty == 0){
            otherList.remove(order); :(*1)
        } else {
            order.setQuantity(orderRemainigQty); //esta linea no es necesaria, ya que order se mantiene original
        } */
        /**/

        return matchResult;
    }


    public MatchResult matchEngineToOrderTaker(double priceCandle, Order order, List<Order> oppositeList) {
        MatchResult matchResult = getMatchResult(priceCandle, order);
        final int qtyOrderStart = order.getQuantity();
        int orderRemainigQty = order.getQuantity();

        if (oppositeList == null || oppositeList.isEmpty()) {
             return matchResult;
        }

        order.setPrice(oppositeList.getFirst().getPrice());
        matchResult.setOrder(order); //(*1)

        while (!oppositeList.isEmpty() && orderRemainigQty > 0) {
            Order firstOrderInOppositeList = oppositeList.getFirst();

            /* Al retornar el matchResul luego de estas validaciones, el matchResult.Order.price no tiene el price
            * de la primera orden opositora.
            * Agregue mas arriba (*1) el pisar la orden default de RandomAlgorithm por la nueva orden (el precio cambio)
            * Agregue mas cambios. Puse break en lugar de retorno. Con el fin de que siga la logica luego del while*/
            if(order.getTypeOrder().equals("buy") && order.getPrice() < oppositeList.getFirst().getPrice()){
                break; // TERCERA MODIFICACION
            }
            if(order.getTypeOrder().equals("sell") && order.getPrice() > oppositeList.getFirst().getPrice()){
                break; // CUARTA MODIFICACION
            }
            /**/

            /* Aqui encontre un problema con el "else". Este retorna matchResult, pero es ilogico.
            * No me permite ejecutar la logica siguiente*/
            if (orderRemainigQty >= firstOrderInOppositeList.getQuantity()) {
                matchResult.setPriceExecution(calculateExecutionPrice(order, firstOrderInOppositeList));
                orderRemainigQty -= firstOrderInOppositeList.getQuantity();
                oppositeList.removeFirst();
            } else {
                firstOrderInOppositeList.setQuantity(firstOrderInOppositeList.getQuantity() - orderRemainigQty);
                matchResult.setPriceExecution(calculateExecutionPrice(order, firstOrderInOppositeList));
                matchResult.setFullyExecuted(true);
//                return matchResult;  PRIMERA MODIFICACION
                orderRemainigQty = 0; //SEGUNDA MOFICACION
            }
            /**/
        }
        if (orderRemainigQty == 0) {
            matchResult.setOrder(null);
        } else {
            matchResult.getOrder().setQuantity(orderRemainigQty);
        }

        matchResult.setQuantityFilled(qtyOrderStart-orderRemainigQty);
        matchResult.setRemainingQuantity(orderRemainigQty);

//        if(orderRemainigQty == 0){ //REVISAR
//            matchResult.setOrder(null); //REVISAR SI ES UTIL ESTA LOGICA. Riesgo detectados: perdida de info
//            matchResult.setFullyExecuted(true);
//        } QUINTA MOFICACION (eliminar este bloque Esta funcion esta repetida en el else del While).

//        if(oppositeList.isEmpty()){
//            return matchResult;
//        } SEXTA MOFICACION (elimnar este bloque No hace falta.)
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
        /* order.setPrice(price); //revisar coherencia en caso de generar matchResult para order Maker. OCTAVA MODIFICACION
        elimine esto para no modificar la Order.*/
        /**/

        /* OCTAVA MODIFICACION. Agregue esto con el fin de clonar la order y
        * no modificar la original*/
        matchResult.setOrder(new Order(order));
        /**/

        /* SEPTIMA MODIFICACION. La agregue gcon el fin de no modificacion la variable
        * Order. revisar coherencia en caso de generar matchResult para order Maker.*/
        matchResult.getOrder().setPrice(priceCandle);
        /**/

        matchResult.setFullyExecuted(false);
        matchResult.setPriceExecution(0);
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