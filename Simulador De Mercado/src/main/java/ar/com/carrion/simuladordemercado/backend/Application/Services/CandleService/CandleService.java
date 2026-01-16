package ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.ICandleDataRepository;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;

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
        candle.setOpen(candle.getOpen());
        candle.setClose(candle.getClose());
        candle.setHigh(candle.getHigh());
        candle.setLow(candle.getLow());
        candle.setTime(candle.getTime());

        candleDataRepository.insertCandle(candle);
    }

    public List<Candle> getAllCandles(){
        return candleDataRepository.findAll();
    }

    public void selectLastTwoCandles(){
        List<Candle> candlesInBD = candleDataRepository.getTwoLastCandle();

        if (candlesInBD.get(1) == null || candlesInBD.get(0) == null){
            startTwoCandleDefault();
            candlesInBD = candleDataRepository.getTwoLastCandle();
        }
        if(candlesInBD.get(0).getOpen() == 0 || candlesInBD.get(1).getOpen() == 0 ||
        candlesInBD.get(0).getClose() == 0 || candlesInBD.get(1).getClose() == 0){
            startTwoCandleDefault();
            candlesInBD = candleDataRepository.getTwoLastCandle();
        }

        candle.setTime(candlesInBD.get(1).getTime());
        candle.setLow(candlesInBD.get(1).getLow());
        candle.setHigh(candlesInBD.get(1).getHigh());
        candle.setOpen(candlesInBD.get(0).getClose());
        candle.setClose(candlesInBD.get(1).getClose());

        System.out.println(candle.getClose());
        System.out.println(candle);
    }


    private void startTwoCandleDefault(){
        Candle candle1 = new Candle();
        candle1.setTime(LocalDateTime.now().minusMinutes(1));
        candle1.setLow((double) 1000 *1.50);
        candle1.setHigh((double) 1000 *0.50);
        candle1.setOpen((double) 1000 *0.80);
        candle1.setClose(1000);

        Candle candle2 = new Candle();
        candle2.setTime(LocalDateTime.now());
        candle2.setLow((double) 1000 *1.50);
        candle2.setHigh((double) 1000 *0.50);
        candle2.setOpen(1000);
        candle2.setClose((double) 1000 *1.20);

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