package ar.com.carrion.simuladordemercado.backend.Application.Shared.MarketStartup;

import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class MarketStartup implements ApplicationRunner {

    private final CandleService candleService;
    private final OrderBookService orderBookService;

    public MarketStartup(OrderBook orderBook, CandleService candleService, OrderBookService orderBookService) {
        this.candleService = candleService;
        this.orderBookService = orderBookService;
    }

    @Override
    public void run(ApplicationArguments args) {
        candleService.selectLastTwoCandles();
        orderBookService.sortOrderBook();
    }
}
