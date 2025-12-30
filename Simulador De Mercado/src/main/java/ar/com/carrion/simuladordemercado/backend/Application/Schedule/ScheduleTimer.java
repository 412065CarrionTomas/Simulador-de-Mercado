package ar.com.carrion.simuladordemercado.backend.Application.Schedule;

import ar.com.carrion.simuladordemercado.backend.Services.CandleService.CandleService;
import org.springframework.scheduling.annotation.Scheduled;


public class ScheduleTimer {

    private final CandleService candleService;

    public ScheduleTimer(CandleService candleService) {
        this.candleService = candleService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void insertCandle(){
        candleService.insertCandle("1m");
        candleService.selectLastCandle();
    }



}
