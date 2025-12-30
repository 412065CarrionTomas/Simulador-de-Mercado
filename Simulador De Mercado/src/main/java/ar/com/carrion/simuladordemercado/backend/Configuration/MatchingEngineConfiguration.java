package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchingEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatchingEngineConfiguration {

    @Bean
    public MatchingEngine matchingEngine(){
        return new MatchingEngine();
    }
}
