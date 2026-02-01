package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.ICandleDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class CandleConfiguration {

    @Bean
    public Candle candle(){
        return new Candle();
    }

    @Bean
    public CandleService candleService(ICandleDataRepository repository,
                                       Candle candle,
                                       CandleNotificationService candleNotificationService){
        return new CandleService(repository, candle, candleNotificationService);
    }

    @Bean
    public CandleNotificationService candleNotificationService(SimpMessagingTemplate template){
        return new CandleNotificationService(template);
    }
}
