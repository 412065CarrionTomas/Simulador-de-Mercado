package ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm.RandomAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchingEngine;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;

public class RandomAlgorithmService {

    private final OrderBookService orderBookService;
    private final Candle candle;
    private final RandomAlgorithm randomAlgorithm;
    private final MatchingEngine matchingEngine;

    public RandomAlgorithmService(OrderBookService orderBookService
            , Candle candle
            , RandomAlgorithm randomAlgorithm
            , MatchingEngine matchingEngine) {
        this.orderBookService = orderBookService;
        this.candle = candle;
        this.randomAlgorithm = randomAlgorithm;
        this.matchingEngine = matchingEngine;
    }

    public void randomAlgorithm(){
        Order order = randomAlgorithm.executeRandom(candle.getClosePrice());
        boolean hasOrderBuy = true;
        MatchResult matchResult;

        if (order.getPrice() == 0.00) {
            if ("buy".equals(order.getTypeOrder())) {
                matchResult = matchingEngine.matchEngineToOrderTaker(candle.getClosePrice(),order,orderBookService.getAllAsks());
                if(matchResult.getOrder() != null && !matchResult.isFullyExecuted()){
                    orderBookService.addLimitBuyOrder(order);
                }
                candle.setClosePrice(matchResult.getPriceExecution());
                modifyCandleExtreme(candle);
                return;
            } else {
                matchResult = matchingEngine.matchEngineToOrderTaker(candle.getClosePrice(),order,orderBookService.getAllBids());
                if(matchResult.getOrder() != null && !matchResult.isFullyExecuted()){
                    orderBookService.addLimitSellOrder(order);
                }
                candle.setClosePrice(matchResult.getPriceExecution());
                modifyCandleExtreme(candle);
                return;
            }
        } else {
            if ("buy".equals(order.getTypeOrder())) {
                orderBookService.addLimitBuyOrder(order);
            } else {
                hasOrderBuy = false;
                orderBookService.addLimitSellOrder(order);
            }
        }

        candle.setClosePrice(matchingEngine.matchEngineToOrder(candle.getClosePrice()
                ,order
                ,hasOrderBuy ? orderBookService.getAllAsks()
                              :orderBookService.getAllBids()
                ,hasOrderBuy ? orderBookService.getAllBids()
                              :orderBookService.getAllAsks()));

        modifyCandleExtreme(candle);
        strings();
    }

    private void modifyCandleExtreme(Candle candle){
        double currentPrice = candle.getClosePrice();

        if(currentPrice > candle.getHighExtremePrice()){
            candle.setHighExtremePrice(currentPrice);
        }

        if(currentPrice < candle.getLowExtremePrice()){
            candle.setLowExtremePrice(currentPrice);
        }
    }

    private void strings(){
        System.out.println(candle.toString());
        orderBookService.printOrderBook();
    }

}
