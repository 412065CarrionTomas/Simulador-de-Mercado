package ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.ICandleDataRepository;
import java.time.LocalDateTime;
import java.util.List;

public class CandleService {

    private final ICandleDataRepository candleDataRepository;
    private final Price value;
    private final Candle candle;

    public CandleService(ICandleDataRepository candleDataRepository, Price value, Candle candle) {
        this.candleDataRepository = candleDataRepository;
        this.value = value;
        this.candle = candle;
    }

    public void insertCandle(){
        candle.setOpenPrice(candle.getOpenPrice());
        candle.setClosePrice(value.getValue());
        candle.setHighExtremePrice(candle.getHighExtremePrice());
        candle.setLowExtremePrice(candle.getLowExtremePrice());
        candle.setTimeFrame(candle.getTimeFrame());
        candle.setTimeClose(candle.getTimeClose());

        candleDataRepository.insertCandle(candle);
    }

    public void selectLastTwoCandles(){
        List<Candle> priceInBD = candleDataRepository.getTwoLastCandle();

        if (priceInBD.get(1) == null || priceInBD.get(0) == null){
            startTwoCandleDefault();
            priceInBD = candleDataRepository.getTwoLastCandle();
        }
        if(priceInBD.get(0).getOpenPrice() == 0 || priceInBD.get(1).getOpenPrice() == 0 ||
        priceInBD.get(0).getClosePrice() == 0 || priceInBD.get(1).getClosePrice() == 0){
            startTwoCandleDefault();
            priceInBD = candleDataRepository.getTwoLastCandle();
        }

        candle.setTimeClose(priceInBD.get(1).getTimeClose());
        candle.setTimeFrame(priceInBD.get(1).getTimeFrame());
        candle.setLowExtremePrice(priceInBD.get(1).getLowExtremePrice());
        candle.setHighExtremePrice(priceInBD.get(1).getHighExtremePrice());
        candle.setOpenPrice(priceInBD.get(0).getClosePrice());

        value.setValue(priceInBD.get(1).getClosePrice());
        candle.setClosePrice(value.getValue());

        System.out.println(value);
        System.out.println(candle);
    }

    private void startTwoCandleDefault(){
        Candle candle1 = new Candle();
        candle1.setTimeClose(LocalDateTime.now().minusMinutes(1));
        candle1.setTimeFrame("1m");
        candle1.setLowExtremePrice((double) 1000 *1.50);
        candle1.setHighExtremePrice((double) 1000 *0.50);
        candle1.setOpenPrice((double) 1000 *0.80);
        candle1.setClosePrice(1000);

        Candle candle2 = new Candle();
        candle2.setTimeClose(LocalDateTime.now());
        candle2.setTimeFrame("1m");
        candle2.setLowExtremePrice((double) 1000 *1.50);
        candle2.setHighExtremePrice((double) 1000 *0.50);
        candle2.setOpenPrice(1000);
        candle2.setClosePrice((double) 1000 *1.20);

        candleDataRepository.insertTwoCandles(candle1,candle2);
    }
}

//    public void resetCandle(double value){
//        Candle candle = new Candle();
//        candle.setClosePrice(value);
//        candle.setOpenPrice(value);
//        candle.setHighExtremePrice(value);
//        candle.setLowExtremePrice(value);
//        candle.setTimeClose(LocalDateTime.now());
//        candle.setTimeFrame("1m");
//
//        candleDataRepository.save(candle);
//    }
//
//    public void selectLastCandle(){
//        Candle candleInBD = candleDataRepository.getLastCandle();
//        value.setValue(candleInBD.getClosePrice());
//    }