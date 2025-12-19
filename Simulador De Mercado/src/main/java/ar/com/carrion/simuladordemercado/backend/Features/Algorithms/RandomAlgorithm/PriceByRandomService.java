package ar.com.carrion.simuladordemercado.backend.Features.Algorithms.RandomAlgorithm;

import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Features.Singleton.PriceSingleton;
import org.springframework.stereotype.Service;

@Service
public class PriceByRandomService {

    private final RandomAlgorithmService _RandomAlgorithmService;
    Price price = PriceSingleton.getInstance();

    public PriceByRandomService(RandomAlgorithmService randomAlgorithmService) {
        _RandomAlgorithmService = randomAlgorithmService;
    }

    public Price ModifyPriceByRandom(){
        price.setClosePrice(_RandomAlgorithmService.RandomPrice(price.getClosePrice()));
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
