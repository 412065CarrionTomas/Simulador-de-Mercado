package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.Candle.ICandleDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CandleConfiguration {

    @Bean
    public CandleService candleService(ICandleDataRepository repository,
                                       Price price){
        return new CandleService(repository,price);
    }
}
