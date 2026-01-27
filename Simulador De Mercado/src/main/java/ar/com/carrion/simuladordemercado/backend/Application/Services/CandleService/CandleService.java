package ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.ICandleDataRepository;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.ClientInfoStatus;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.spi.CalendarDataProvider;

public class CandleService {

    private final ICandleDataRepository candleDataRepository;
    private final Candle candle;

    public CandleService(ICandleDataRepository candleDataRepository, Candle candle) {
        this.candleDataRepository = candleDataRepository;
        this.candle = candle;
    }

    public void insertCandle(String timeFrame){
        candle.setOpen(candle.getOpen());
        candle.setClose(candle.getClose());
        candle.setHigh(candle.getHigh());
        candle.setLow(candle.getLow());
        candle.setTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        candle.setTimeFrame(timeFrame);

        candleDataRepository.insertCandle(candle);
    }

    public void buildAndInsertCandle(int quantity, String timeFrame, String newCandleTimeFrame){
        if (timeFrame.equals("M1") || timeFrame.equals("M5")){ //revisar con claude
            throw new IllegalArgumentException("You must enter a reasonable candlestick value. M1 and M5. For the moment...");
        }

        List<Candle> candlesInBD = candleDataRepository.getNCandleInXTimeFrame(quantity,timeFrame);

        String timeFrameType = newCandleTimeFrame.substring(1);
        int timeFrameMany = Integer.parseInt(newCandleTimeFrame.substring(2));
        String newTimeFrameType = newCandleTimeFrame.substring(1);
        int newTimeFrameMany = Integer.parseInt(newCandleTimeFrame.substring(2));

        int difference = takeDifference(timeFrameType,timeFrameMany,newTimeFrameType,newTimeFrameMany);

        if(candlesInBD.size() < difference){
            throw new IllegalArgumentException("There is no such quantity of candles from that time.");
        }

        Candle newCandle = new Candle();

        newCandle.setOpen(candlesInBD.getLast().getOpen());
        newCandle.setLow(candlesInBD.getLast().getLow());
        newCandle.setHigh(candlesInBD.getLast().getHigh());
        newCandle.setClose(candlesInBD.getFirst().getClose());
    }

    private int takeDifference(String timeFrameType,int timeFrameMany,String newTimeFrameType,int newTimeFrameMany){
        int difference = 0;
        switch (timeFrameType) {
            case "M":
                if (newTimeFrameType.equals("M")) {
                    if(newTimeFrameMany == 1){
                        difference = 0;
                        break;
                    }
                    difference = newTimeFrameMany/timeFrameMany;
                    break;
                }
                if(newTimeFrameType.equals("H")){
                    difference = (60*newTimeFrameMany)/timeFrameMany;
                    break;
                }
                if (newTimeFrameType.equals("D")){
                    difference = (1440*newTimeFrameMany)/timeFrameMany;
                    break;
                }
                break;
            case "H":
                if (newTimeFrameType.equals("M")) {
                    throw new IllegalArgumentException("You cannot build backwards. You cannot build smaller candles with longer times.");
                }
                if(newTimeFrameType.equals("H")){
                    if(newTimeFrameMany == 1){
                        difference = 0;
                        break;
                    }
                    difference = newTimeFrameMany/timeFrameMany;
                    break;
                }
                if (newTimeFrameType.equals("D")){
                    difference = (60*newTimeFrameMany)/timeFrameMany;
                    break;
                }
                break;

            case "D":
                if (newTimeFrameType.equals("M")) {
                    throw new IllegalArgumentException("You cannot build backwards. You cannot build smaller candles with longer times.");
                }
                if(newTimeFrameType.equals("H")){
                    throw new IllegalArgumentException("You cannot build backwards. You cannot build smaller candles with longer times.");
                }
                if (newTimeFrameType.equals("D")){
                    if(newTimeFrameMany == 1){
                        difference = 0;
                        break;
                    }
                    difference = newTimeFrameMany/timeFrameMany;
                    break;
                }
                break;
        }
        return difference;
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
        candle1.setTimeFrame("M1");

        candleDataRepository.insertTwoCandles(candle1,candle2);
    }
}