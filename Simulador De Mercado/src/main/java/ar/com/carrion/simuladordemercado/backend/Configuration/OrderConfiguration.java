package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public Order order(){
        return new Order();
    }
}
