package ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm.RandomAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchingEngine;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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
//        publishOrderCreatedEvent(order);
        boolean hasOrderBuy = true;
        MatchResult matchResult;

        if (order.getPrice() == 0.00) {

            if ("buy".equals(order.getTypeOrder())) {
                matchResult = matchingEngine.matchEngineToOrderTaker(candle.getClose(),order,orderBookService.getAllAsks());

                if(matchResult.getOrder() != null && !matchResult.isFullyExecuted()){
                    orderBookService.addLimitBuyOrder(order);
                }

                candle.setClose(matchResult.getPriceExecution());
                modifyCandleExtreme(candle);
//                eventPublisher.publishEvent(new OrderBookUpdateEvent("ORDER_BOOK_UPDATE"));
                return;

            } else {
                matchResult = matchingEngine.matchEngineToOrderTaker(candle.getClose(),order,orderBookService.getAllBids());

                if(matchResult.getOrder() != null && !matchResult.isFullyExecuted()){
                    orderBookService.addLimitSellOrder(order);
                }

                candle.setClose(matchResult.getPriceExecution());
                modifyCandleExtreme(candle);
//                eventPublisher.publishEvent(new OrderBookUpdateEvent("ORDER_BOOK_UPDATE"));
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

        candle.setClose(matchingEngine.matchEngineToOrder(candle.getClose()
                ,order
                ,hasOrderBuy ? orderBookService.getAllAsks()
                              :orderBookService.getAllBids()
                ,hasOrderBuy ? orderBookService.getAllBids()
                              :orderBookService.getAllAsks()));

        modifyCandleExtreme(candle);
//        eventPublisher.publishEvent(new OrderBookUpdateEvent("ORDER_BOOK_UPDATE"));
    }

    private void publishOrderUpdateEvent(Order order, MatchResult matchResult){
        eventPublisher.publishEvent(new OrderBookUpdateEvent(
//
//                LocalDateTime.now(ZoneOffset.UTC),
//                order,
                matchResult
        ));
    }

    private void publishOrderCreatedEvent(Order order, MatchResult matchResult) {
        eventPublisher.publishEvent(new OrderBookUpdateEvent(
                matchResult
//                "CREATED",
//                LocalDateTime.now(ZoneOffset.UTC),
//                order,
//                0,
//                false
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
