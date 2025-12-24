package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Shared.MarketStartup.MarketStartup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketStartupConfiguration {

    @Bean
    public MarketStartup marketStartup(CandleService candleService,
                                       OrderBook orderBook,
                                       OrderBookService orderBookService){
        return new MarketStartup(candleService
                ,orderBook
                ,orderBookService);
    }
}
