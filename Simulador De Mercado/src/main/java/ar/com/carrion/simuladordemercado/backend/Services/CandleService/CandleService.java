package ar.com.carrion.simuladordemercado.backend.Services.CandleService;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.Candle.ICandleDataRepository;

import java.time.LocalDateTime;

public class CandleService {

    private final ICandleDataRepository candleDataRepository;
    private final Price price;

    public CandleService(ICandleDataRepository candleDataRepository, Price price) {
        this.candleDataRepository = candleDataRepository;
        this.price = price;
    }

    public void insertCandle(String timeFrame){
        Candle newCandle = new Candle();
        if(price.getClosePrice() == 0){
            return;
        }
        if(price.getOpenPrice() == 0){
            return;
        }
        newCandle.setOpenPrice(price.getOpenPrice());
        newCandle.setClosePrice(price.getClosePrice());
        newCandle.setHighExtremePrice(price.getHighExtremePrice());
        newCandle.setLowExtremePrice(price.getLowExtremePrice());
        newCandle.setTimeFrame(timeFrame);
        newCandle.setTimeClose(LocalDateTime.now());

        candleDataRepository.insertCandle(newCandle);
    }

    public void resetCandle(double price){
        Candle candle = new Candle();
        candle.setClosePrice(price);
        candle.setOpenPrice(price);
        candle.setHighExtremePrice(price);
        candle.setLowExtremePrice(price);
        candle.setTimeClose(LocalDateTime.now());
        candle.setTimeFrame("1m");

        candleDataRepository.save(candle);
    }

    public Price selectLastCandle(){
        Candle priceInBD = candleDataRepository.getLastCandle();
        price.setHighExtremePrice(priceInBD.getHighExtremePrice());
        price.setLowExtremePrice(priceInBD.getLowExtremePrice());
        price.setClosePrice(priceInBD.getClosePrice());
        price.setOpenPrice(priceInBD.getClosePrice());
        System.out.println(price);
        return price;
    }


//    public Price selectLastCandle(){
//        List<Candle> priceInBD = candleDataRepository.getTwoLastCandle();
//        price.setHighExtremePrice(priceInBD.get(1).getHighExtremePrice());
//        price.setLowExtremePrice(priceInBD.get(1).getLowExtremePrice());
//        price.setClosePrice(priceInBD.get(1).getClosePrice());
//        price.setOpenPrice(priceInBD.get(0).getClosePrice());
//        System.out.println(price);
//        return price;
//    }
}
