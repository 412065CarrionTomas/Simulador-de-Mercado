package ar.com.carrion.simuladordemercado.backend.Application.Shared.MarketStartup;

import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderService.OrderService;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class MarketStartup implements ApplicationRunner {

    private final CandleService candleService;
    private final OrderBookService orderBookService;
    private final OrderService orderService;

    public MarketStartup(OrderBook orderBook, CandleService candleService, OrderBookService orderBookService, OrderService orderService) {
        this.candleService = candleService;
        this.orderBookService = orderBookService;
        this.orderService = orderService;
    }

    @Override
    public void run(ApplicationArguments args) {
        orderService.selectAllOrderBook();
        candleService.selectLastTwoCandles();
        orderBookService.sortOrderBook();
    }
}
