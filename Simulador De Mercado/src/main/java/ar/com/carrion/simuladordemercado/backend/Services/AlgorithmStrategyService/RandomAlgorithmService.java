package ar.com.carrion.simuladordemercado.backend.Services.AlgorithmStrategyService;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Services.OrderBookService.OrderBookService;

import java.util.Objects;

public class RandomAlgorithmService {

    private final OrderBookService orderBookService;
    private final Price price;
    private final AlgorithmStrategyService algorithmStrategyService;

    public RandomAlgorithmService(OrderBookService orderBookService, Price price, AlgorithmStrategyService algorithmStrategyService) {
        this.orderBookService = orderBookService;
        this.price = price;
        this.algorithmStrategyService = algorithmStrategyService;
    }

    public void randomAlgorithm(){
        Order order = algorithmStrategyService.runAlgorithm("RandomAlgorithm", price.getPrice());

        if (order.getPrice() == null && order.getQuantity() == 0) {
            if ("buy".equals(order.getTypeOrder())) {
                orderBookService.takeBuyOrder();
            } else {
                orderBookService.takeSellOrder();
            }
        } else {
            if ("buy".equals(order.getTypeOrder())) {
                orderBookService.addBuyOrder(order);
            } else {
                orderBookService.addSellOrder(order);
            }
        }
    }


}
