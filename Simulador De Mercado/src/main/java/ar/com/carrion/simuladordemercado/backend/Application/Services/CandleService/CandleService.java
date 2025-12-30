//package ar.com.carrion.simuladordemercado.backend.Services.CandleService;
//
//import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
//import ar.com.carrion.simuladordemercado.backend.Domains.Price;
//import ar.com.carrion.simuladordemercado.backend.Infrastructure.Candle.ICandleDataRepository;
//
//import java.time.LocalDateTime;
//
//public class CandleService {
//
//    private final ICandleDataRepository candleDataRepository;
//    private final Price value;
//
//    public CandleService(ICandleDataRepository candleDataRepository, Price value) {
//        this.candleDataRepository = candleDataRepository;
//        this.value = value;
//    }
//
//    public void insertCandle(String timeFrame){
//        Candle newCandle = new Candle();
//        if(value.getClosePrice() == 0){
//            return;
//        }
//        if(value.getOpenPrice() == 0){
//            return;
//        }
//        newCandle.setOpenPrice(value.getOpenPrice());
//        newCandle.setClosePrice(value.getClosePrice());
//        newCandle.setHighExtremePrice(value.getHighExtremePrice());
//        newCandle.setLowExtremePrice(value.getLowExtremePrice());
//        newCandle.setTimeFrame(timeFrame);
//        newCandle.setTimeClose(LocalDateTime.now());
//
//        candleDataRepository.insertCandle(newCandle);
//    }
//
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
//    public Price selectLastCandle(){
//        Candle priceInBD = candleDataRepository.getLastCandle();
//        value.setHighExtremePrice(priceInBD.getHighExtremePrice());
//        value.setLowExtremePrice(priceInBD.getLowExtremePrice());
//        value.setClosePrice(priceInBD.getClosePrice());
//        value.setOpenPrice(priceInBD.getClosePrice());
//        System.out.println(value);
//        return value;
//    }
//

//    public Price selectLastCandle(){
//        List<Candle> priceInBD = candleDataRepository.getTwoLastCandle();
//        value.setHighExtremePrice(priceInBD.get(1).getHighExtremePrice());
//        value.setLowExtremePrice(priceInBD.get(1).getLowExtremePrice());
//        value.setClosePrice(priceInBD.get(1).getClosePrice());
//        value.setOpenPrice(priceInBD.get(0).getClosePrice());
//        System.out.println(value);
//        return value;
//    }
//}
