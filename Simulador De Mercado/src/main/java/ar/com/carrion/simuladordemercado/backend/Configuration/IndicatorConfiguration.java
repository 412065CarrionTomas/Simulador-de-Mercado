package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarket;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.PublicKey;

@Configuration
public class IndicatorConfiguration {
    @Bean
    public DepthOfMarket depthOfMarket(){
        return new DepthOfMarket();
    }

    @Bean
    public DepthOfMarketService depthOfMarketService(DepthOfMarket depthOfMarket,
                                                     OrderBookService orderBookService) {
        return new DepthOfMarketService( depthOfMarket, orderBookService);
    }

    @Bean
    public DepthOfMarketNotificationService depthOfMarketNotificationService(SimpMessagingTemplate template){
        return new DepthOfMarketNotificationService(template);
    }

}
