package ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm.IdOrder;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm.RandomAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchingEngine;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderCreateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderUpdateEvent;
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
    private final IdOrder idOrder;


    public RandomAlgorithmService(OrderBookService orderBookService
            , Candle candle
            , RandomAlgorithm randomAlgorithm
            , MatchingEngine matchingEngine
            , ApplicationEventPublisher eventPublisher
            , IdOrder idOrder) {
        this.orderBookService = orderBookService;
        this.candle = candle;
        this.randomAlgorithm = randomAlgorithm;
        this.matchingEngine = matchingEngine;
        this.eventPublisher = eventPublisher;
        this.idOrder = idOrder;
    }

    public void randomAlgorithm(){
        Order order = randomAlgorithm.executeRandom(getNewIdOrder(), candle.getClose());
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
                publishOrderUpdateEvent(matchResult);
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
                publishOrderUpdateEvent(matchResult);
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
    }


    private void publishOrderUpdateEvent(MatchResult matchResult){
        eventPublisher.publishEvent(new OrderUpdateEvent(
                matchResult,
                LocalDateTime.now(ZoneOffset.UTC)
        ));
    }

    private void publishOrderCreatedEvent(Order order) {
        eventPublisher.publishEvent(new OrderCreateEvent(
                "CREATE",
                LocalDateTime.now(ZoneOffset.UTC),
                order
        ));
    }

    private Long getNewIdOrder(){
        Long id = idOrder.getIdOrder();
        if(id == null){
            idOrder.setIdOrder(1L);
            return 1L;
        }else {
            idOrder.setIdOrder(id+1);
            return id+1;
        }
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
