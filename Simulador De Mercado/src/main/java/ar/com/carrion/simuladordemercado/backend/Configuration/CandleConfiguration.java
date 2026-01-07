package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.ICandleDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CandleConfiguration {

    @Bean
    public Candle candle(){
        return new Candle();
    }

    @Bean
    public CandleService candleService(ICandleDataRepository repository,
                                       Price price,
                                       Candle candle){
        return new CandleService(repository,price,candle);
    }
}
