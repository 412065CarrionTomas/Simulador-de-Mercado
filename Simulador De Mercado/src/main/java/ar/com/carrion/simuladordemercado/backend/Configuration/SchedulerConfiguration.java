package ar.com.carrion.simuladordemercado.backend.Configuration;

import ar.com.carrion.simuladordemercado.backend.Schedule.ScheduleTimer;
import ar.com.carrion.simuladordemercado.backend.Services.CandleService.CandleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfiguration {

    @Bean
    public ScheduleTimer scheduleTimer(CandleService service){
        return new ScheduleTimer(service);
    }
}
