package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderService.OrderService;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.MarketStartup.MarketStartup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketStartupConfiguration {

    @Bean
    public MarketStartup marketStartup(OrderService orderService
            , CandleService candleService
            , OrderBook orderBook
            , OrderBookService orderBookService){
        return new MarketStartup(orderBook, candleService, orderBookService, orderService);
    }
}
