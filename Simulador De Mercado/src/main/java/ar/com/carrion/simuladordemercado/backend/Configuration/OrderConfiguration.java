package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderService.OrderService;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.IOrderDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public Order order(){
        return new Order();
    }

    @Bean
    public OrderService orderService(OrderBook orderBook,
                                     IOrderDataRepository orderDataRepository){
        return new OrderService(orderBook,
                orderDataRepository);
    }
}
