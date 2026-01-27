package ar.com.carrion.simuladordemercado.backend.Application.Schedule;

import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import org.springframework.scheduling.annotation.Scheduled;


public class ScheduleTimer {

    private final CandleService candleService;

    public ScheduleTimer(CandleService candleService) {
        this.candleService = candleService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void insertCandleM1(){
        candleService.insertCandle("M1");
        candleService.selectLastTwoCandles();
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void insertCandleM5(){
        candleService.insertCandle("M5");
        candleService.selectLastTwoCandles();
    }



}
