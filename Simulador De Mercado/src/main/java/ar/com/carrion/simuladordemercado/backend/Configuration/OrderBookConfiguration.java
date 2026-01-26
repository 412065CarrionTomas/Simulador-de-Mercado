package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookNotificationService;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class OrderBookConfiguration {
    @Bean
    public OrderBook orderBook(){
        return new OrderBook();
    }

    @Bean
    public OrderBookService orderBookService(OrderBook orderBook,
                                             OrderBookNotificationService notificationService){
        return new OrderBookService(orderBook,
                notificationService);
    }

    @Bean
    public OrderBookNotificationService orderBookNotificationService(SimpMessagingTemplate template){
        return new OrderBookNotificationService(template);
    }
}
