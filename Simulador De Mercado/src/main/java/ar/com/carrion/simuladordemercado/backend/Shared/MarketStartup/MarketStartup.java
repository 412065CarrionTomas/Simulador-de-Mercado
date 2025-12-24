package ar.com.carrion.simuladordemercado.backend.Shared.MarketStartup;

import ar.com.carrion.simuladordemercado.backend.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Services.OrderBookService.OrderBookService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class MarketStartup implements ApplicationRunner {

    private final CandleService candleService;
    private final OrderBookService orderBookService;

    public MarketStartup(CandleService candleService, OrderBookService orderBookService) {
        this.candleService = candleService;
        this.orderBookService = orderBookService;
    }

    @Override
    public void run(ApplicationArguments args) {
        candleService.selectLastCandle();
        orderBookService.sortOrderBook();
    }
}
