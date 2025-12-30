package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Algorithm.RandomAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchingEngine;
import ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService.RandomAlgorithmService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlgorithmConfiguration {

    @Bean
    public RandomAlgorithm randomAlgorithm(){
        return new RandomAlgorithm();
    }

    @Bean
    public RandomAlgorithmService randomAlgorithmService(OrderBookService orderBookService
    , Price price
    , RandomAlgorithm randomAlgorithm
    , MatchingEngine matchingEngine){
        return new RandomAlgorithmService(orderBookService, price, randomAlgorithm, matchingEngine);
    }

}
