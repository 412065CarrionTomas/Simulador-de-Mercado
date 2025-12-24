package ar.com.carrion.simuladordemercado.backend.Services.PriceService;

import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Algorithm.RandomAlgorithm;


public class PriceByRandomService {

    private final RandomAlgorithm _RandomAlgorithm;
    private final Price price;
    private final OrderBook orderBook;

    public PriceByRandomService(RandomAlgorithm randomAlgorithm, Price price, OrderBook orderBook) {
        _RandomAlgorithm = randomAlgorithm;
        this.price = price;
        this.orderBook = orderBook;
    }

    public Price ModifyPriceByRandom(){
        price.setClosePrice(_RandomAlgorithm.RandomPrice(price.getClosePrice()));
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
