package ar.com.carrion.simuladordemercado.backend.Application.Schedule;

import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import org.springframework.scheduling.annotation.Scheduled;


public class ScheduleTimer {

    private final CandleService candleService;
    private int countM5 = 0;
    private int countM10 = 0;

    public ScheduleTimer(CandleService candleService) {
        this.candleService = candleService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void insertCandles() throws InterruptedException {
        candleService.insertCandle("M1");

        countM5 +=1;
        countM10 +=1;

        if (countM5 == 5){
            candleService.buildAndInsertCandle("M1", "M5");
            countM5 =0;
            Thread.sleep(2000);
        }

        if( countM10 ==10){
            candleService.buildAndInsertCandle("M1", "M10");
            countM10 =0;
            Thread.sleep(4000);
        }

        candleService.selectLastTwoCandles();
    }
}
