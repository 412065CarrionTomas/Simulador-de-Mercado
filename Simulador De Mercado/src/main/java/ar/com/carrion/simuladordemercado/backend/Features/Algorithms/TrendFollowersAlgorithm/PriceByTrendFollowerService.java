package ar.com.carrion.simuladordemercado.backend.Features.Algorithms.TrendFollowersAlgorithm;

import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Features.Singleton.PriceSingleton;
import org.springframework.stereotype.Service;

@Service
public class PriceByTrendFollowerService {
    private final TrendFollowersAlgorithmService _TrendFollowersAlgorithm;
    Price price = PriceSingleton.getInstance();

    public PriceByTrendFollowerService(TrendFollowersAlgorithmService trendFollowersAlgorithm) {
        _TrendFollowersAlgorithm = trendFollowersAlgorithm;
    }

    public Price modifyPriceByTrendFollower(){
        price.setClosePrice(_TrendFollowersAlgorithm.randomPrice(price.getClosePrice()));
        modifyPriceExtreme(price);
        return price;
    }

    private void modifyPriceExtreme(Price lastPrice){
        double currentPrice = lastPrice.getClosePrice();

        if(currentPrice > lastPrice.getHighExtremePrice()){
            lastPrice.setHighExtremePrice(currentPrice);
        }

        if(currentPrice < lastPrice.getLowExtremePrice()){
            lastPrice.setLowExtremePrice(currentPrice);
        }
    }
}
