package ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm.RandomAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchingEngine;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;

public class RandomAlgorithmService {

    private final OrderBookService orderBookService;
    private final Price price;
    private final Candle candle;
    private final RandomAlgorithm randomAlgorithm;
    private final MatchingEngine matchingEngine;

    public RandomAlgorithmService(OrderBookService orderBookService
            , Price price
            , Candle candle
            , RandomAlgorithm randomAlgorithm
            , MatchingEngine matchingEngine) {
        this.orderBookService = orderBookService;
        this.price = price;
        this.candle = candle;
        this.randomAlgorithm = randomAlgorithm;
        this.matchingEngine = matchingEngine;
    }

    public void randomAlgorithm(){
        Order order = randomAlgorithm.executeRandom(price.getValue());
        boolean hasOrderBuy = true;

        if (order.getPrice() == null) {
            if ("buy".equals(order.getTypeOrder())) {
                price.setValue(matchingEngine.matchEngineToOrderTaker(price.getValue(),order, orderBookService.getAllAsks()));
                return;
            } else {
                price.setValue(matchingEngine.matchEngineToOrderTaker(price.getValue(),order, orderBookService.getAllBids()));
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

        price.setValue(matchingEngine.matchEngineToOrder(price.getValue()
                ,order
                ,hasOrderBuy ? orderBookService.getAllAsks()
                              :orderBookService.getAllBids()
                ,hasOrderBuy ? orderBookService.getAllBids()
                              :orderBookService.getAllAsks()));

        modifyPriceExtreme(price,candle);
    }

    private void modifyPriceExtreme(Price price, Candle candle){
        double currentPrice = price.getValue();

        if(currentPrice > candle.getHighExtremePrice()){
            candle.setHighExtremePrice(currentPrice);
        }

        if(currentPrice < candle.getLowExtremePrice()){
            candle.setLowExtremePrice(currentPrice);
        }
    }

}
