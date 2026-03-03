package ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm.RandomAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchingEngine;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderCreateEvent;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class RandomAlgorithmService {

    private final OrderBookService orderBookService;
    private final Candle candle;
    private final RandomAlgorithm randomAlgorithm;
    private final MatchingEngine matchingEngine;
    private final ApplicationEventPublisher eventPublisher;

    public RandomAlgorithmService(OrderBookService orderBookService
            , Candle candle
            , RandomAlgorithm randomAlgorithm
            , MatchingEngine matchingEngine, ApplicationEventPublisher eventPublisher) {
        this.orderBookService = orderBookService;
        this.candle = candle;
        this.randomAlgorithm = randomAlgorithm;
        this.matchingEngine = matchingEngine;
        this.eventPublisher = eventPublisher;
    }

    public void randomAlgorithm(){
        Order order = randomAlgorithm.executeRandom(candle.getClose());
        publishOrderCreatedEvent(order);
        boolean hasOrderBuy = true;
        MatchResult matchResult;

        if (order.getPrice() == 0.00) {

            if ("buy".equals(order.getTypeOrder())) {
                matchResult = matchingEngine.matchEngineToOrderTaker(candle.getClose(),order,orderBookService.getAllAsks());

                if(matchResult.getOrder() != null && !matchResult.isFullyExecuted() && matchResult.getOrder().getPrice() != 0){
                    orderBookService.addLimitBuyOrder(matchResult.getOrder());
                }

                candle.setClose((matchResult.getPriceExecution()) == 0
                                ? candle.getClose()
                                : matchResult.getPriceExecution());
                modifyCandleExtreme(candle);
                publishOrderUpdateEvent(order,matchResult);
                return;

            } else {
                matchResult = matchingEngine.matchEngineToOrderTaker(candle.getClose(),order,orderBookService.getAllBids());

                if(matchResult.getOrder() != null && !matchResult.isFullyExecuted() && matchResult.getOrder().getPrice() != 0){
                    orderBookService.addLimitSellOrder(matchResult.getOrder());
                }

                candle.setClose((matchResult.getPriceExecution()) == 0
                        ? candle.getClose()
                        : matchResult.getPriceExecution());
                modifyCandleExtreme(candle);
                publishOrderUpdateEvent(order,matchResult);
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

        matchResult = matchingEngine.matchEngineToOrder(candle.getClose()
                ,order
                ,hasOrderBuy ? orderBookService.getAllAsks()
                            :orderBookService.getAllBids()
                ,hasOrderBuy ? orderBookService.getAllBids()
                            :orderBookService.getAllAsks());

        candle.setClose((matchResult.getPriceExecution()) == 0
                ? candle.getClose()
                : matchResult.getPriceExecution());

        modifyCandleExtreme(candle);
//        publishOrderUpdateEvent(order,matchResult); //agregar retorno de matchResult al MatchEngine
    }


    private void publishOrderUpdateEvent(Order order, MatchResult matchResult){
        eventPublisher.publishEvent(new OrderBookUpdateEvent(
                LocalDateTime.now(ZoneOffset.UTC),
                order,
                matchResult
        ));
    }

    private void publishOrderCreatedEvent(Order order) {
        eventPublisher.publishEvent(new OrderCreateEvent(
                "CREATE",
                LocalDateTime.now(ZoneOffset.UTC),
                order
        ));
    }

    private void modifyCandleExtreme(Candle candle){
        double currentPrice = candle.getClose();

        if(currentPrice > candle.getHigh()){
            candle.setHigh(currentPrice);
        }

        if(currentPrice < candle.getLow()){
            candle.setLow(currentPrice);
        }
    }

}
