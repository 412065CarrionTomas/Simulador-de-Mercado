package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Services.OrderBookService.OrderBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderBookConfiguration {
    @Bean
    public OrderBook orderBook(){
        return new OrderBook();
    }

    @Bean
    public OrderBookService orderBookService(OrderBook orderBook){
        return new OrderBookService(orderBook);
    }
}
