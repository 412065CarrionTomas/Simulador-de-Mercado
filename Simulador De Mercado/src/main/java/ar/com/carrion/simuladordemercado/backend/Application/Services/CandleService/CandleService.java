package ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.ICandleDataRepository;
import java.time.LocalDateTime;
import java.util.List;

public class CandleService {

    private final ICandleDataRepository candleDataRepository;
    private final Candle candle;

    public CandleService(ICandleDataRepository candleDataRepository, Candle candle) {
        this.candleDataRepository = candleDataRepository;
        this.candle = candle;
    }

    public void insertCandle(){
        candle.setOpenPrice(candle.getOpenPrice());
        candle.setClosePrice(candle.getClosePrice());
        candle.setHighExtremePrice(candle.getHighExtremePrice());
        candle.setLowExtremePrice(candle.getLowExtremePrice());
        candle.setTimeFrame(candle.getTimeFrame());
        candle.setTimeClose(candle.getTimeClose());

        candleDataRepository.insertCandle(candle);
    }

    public void selectLastTwoCandles(){
        List<Candle> candlesInBD = candleDataRepository.getTwoLastCandle();

        if (candlesInBD.get(1) == null || candlesInBD.get(0) == null){
            startTwoCandleDefault();
            candlesInBD = candleDataRepository.getTwoLastCandle();
        }
        if(candlesInBD.get(0).getOpenPrice() == 0 || candlesInBD.get(1).getOpenPrice() == 0 ||
        candlesInBD.get(0).getClosePrice() == 0 || candlesInBD.get(1).getClosePrice() == 0){
            startTwoCandleDefault();
            candlesInBD = candleDataRepository.getTwoLastCandle();
        }

        candle.setTimeClose(candlesInBD.get(1).getTimeClose());
        candle.setTimeFrame(candlesInBD.get(1).getTimeFrame());
        candle.setLowExtremePrice(candlesInBD.get(1).getLowExtremePrice());
        candle.setHighExtremePrice(candlesInBD.get(1).getHighExtremePrice());
        candle.setOpenPrice(candlesInBD.get(0).getClosePrice());
        candle.setClosePrice(candlesInBD.get(1).getClosePrice());

        System.out.println(candle.getClosePrice());
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