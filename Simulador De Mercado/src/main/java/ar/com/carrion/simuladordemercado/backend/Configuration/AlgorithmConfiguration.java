package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Services.PriceService.PriceByRandomService;
import ar.com.carrion.simuladordemercado.backend.Algorithm.RandomAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Services.PriceService.PriceByTrendFollowerService;
import ar.com.carrion.simuladordemercado.backend.Algorithm.TrendFollowersAlgorithm;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.Candle.ICandleDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlgorithmConfiguration {

    @Bean
    public RandomAlgorithm randomAlgorithm(OrderBookService orderBookService){
        return new RandomAlgorithm(orderBookService);
    }

//    @Bean
//    public PriceByRandomService priceByRandomService(RandomAlgorithm algorithm,
//                                                     Price price){
//        return new PriceByRandomService(algorithm,price);
//    }
//
//    public TrendFollowersAlgorithm trendFollowersAlgorithm(ICandleDataRepository repository){
//        return new TrendFollowersAlgorithm(repository);
//    }
//
//    @Bean
//    public PriceByTrendFollowerService priceByTrendFollowerService(TrendFollowersAlgorithm algorithm,
//                                                                   Price price){
//        return new PriceByTrendFollowerService(algorithm, price);
//    }
}
