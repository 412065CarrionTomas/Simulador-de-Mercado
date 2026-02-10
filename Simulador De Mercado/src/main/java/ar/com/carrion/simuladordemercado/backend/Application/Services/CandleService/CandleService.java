package ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.ICandleDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class CandleService {

    private final ICandleDataRepository candleDataRepository;
    private final Candle candle;
    private final CandleNotificationService candleNotificationService;

    public CandleService(ICandleDataRepository candleDataRepository, Candle candle, CandleNotificationService candleNotificationService) {
        this.candleDataRepository = candleDataRepository;
        this.candle = candle;
        this.candleNotificationService = candleNotificationService;
    }

    public void insertCandle(String timeFrame){
        candle.setOpen(candle.getOpen());
        candle.setClose(candle.getClose());
        candle.setHigh(candle.getHigh());
        candle.setLow(candle.getLow());
        candle.setTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        candle.setTimeFrame(timeFrame);

        candleDataRepository.insertCandle(candle);
        candleNotificationService.notifyCandleUpdate(candle);
    }

    public void buildAndInsertCandle(String timeFrame, String newCandleTimeFrame){
        String timeFrameType = timeFrame.substring(0, 1);
        int timeFrameMany = Integer.parseInt(timeFrame.substring(1));
        String newTimeFrameType = newCandleTimeFrame.substring(0, 1);
        int newTimeFrameMany = Integer.parseInt(newCandleTimeFrame.substring(1));

        int necessaryCandles = takeNecessaryCandles(timeFrameType,newTimeFrameType,timeFrameMany,newTimeFrameMany);

        try {
            List<Candle> candlesInBD = candleDataRepository.getNCandleInXTimeFrame(necessaryCandles, timeFrame);
            Candle newCandle = buildNewCandle(candlesInBD, newCandleTimeFrame);
            candleDataRepository.insertCandle(newCandle);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error building candle: " + e.getMessage(), e);
        }

    }

    private int takeNecessaryCandles(String timeFrameType, String newTimeFrameType,
                                        int timeFrameMany, int newTimeFrameMany){
        int baseMinutes = convertToMinutes(timeFrameType, timeFrameMany);
        int targetMinutes = convertToMinutes(newTimeFrameType, newTimeFrameMany);

        if (baseMinutes > targetMinutes) {
            throw new IllegalArgumentException(
                    "You cannot build backwards. You cannot build smaller candles with longer times."
            );
        }
        return targetMinutes/baseMinutes;
    }

    private int convertToMinutes(String timeFrameType, int timeFrameMany){
        int minutesPerHour = 60;
        int minutesPerDay = 1440;
        return switch (timeFrameType){
            case "M" -> timeFrameMany;
            case "H" -> timeFrameMany*minutesPerHour;
            case "D" -> timeFrameMany*minutesPerDay;
            default ->  throw new IllegalArgumentException("Invalid timeFrameType: " + timeFrameType);
        };
    }

    private Candle buildNewCandle(List<Candle> candles, String timeFrame) {
        Candle newCandle = new Candle();
        newCandle.setOpen(candles.getLast().getOpen());
        newCandle.setClose(candles.getFirst().getClose());
        newCandle.setLow(candles.stream().mapToDouble(Candle::getLow).min().orElse(0));
        newCandle.setHigh(candles.stream().mapToDouble(Candle::getHigh).max().orElse(0));
        newCandle.setTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        newCandle.setTimeFrame(timeFrame);
        return newCandle;
    }



    public List<Candle> getAllCandles(){
        return candleDataRepository.findAll();
    }

    public void selectLastTwoCandles(){
        List<Candle> candlesInBD = candleDataRepository.getTwoLastCandle();

        if(candlesInBD.size() == 0){
            startTwoCandleDefault();
            candlesInBD = candleDataRepository.getTwoLastCandle();
        }

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
        candle.setTimeFrame(candlesInBD.get(1).getTimeFrame());
    }


    private void startTwoCandleDefault(){
        Candle candle1 = new Candle();
        candle1.setTime(LocalDateTime.now().minusMinutes(1).toEpochSecond(ZoneOffset.UTC));
        candle1.setLow(2000.0);
        candle1.setHigh(5000.0);
        candle1.setOpen(3000.0);
        candle1.setClose(4000);
        candle1.setTimeFrame("M1");

        Candle candle2 = new Candle();
        candle2.setTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        candle2.setLow(2000.0);
        candle2.setHigh(6000.0);
        candle2.setOpen(4000);
        candle2.setClose(6000.0);
        candle2.setTimeFrame("M1");

        candleDataRepository.insertTwoCandles(candle1,candle2);
    }
}