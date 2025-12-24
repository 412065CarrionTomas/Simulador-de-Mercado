package ar.com.carrion.simuladordemercado.backend.Services.PriceService;

import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Algorithm.TrendFollowersAlgorithm;


public class PriceByTrendFollowerService {
    private final TrendFollowersAlgorithm _TrendFollowersAlgorithm;
    private final Price price;

    public PriceByTrendFollowerService(TrendFollowersAlgorithm trendFollowersAlgorithm, Price price) {
        _TrendFollowersAlgorithm = trendFollowersAlgorithm;
        this.price = price;
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
