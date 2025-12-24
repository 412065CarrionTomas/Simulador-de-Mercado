package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Services.CandleService.CandleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class StartController {

    private final CandleService _CandleService;

    public StartController(CandleService candleService) {
        _CandleService = candleService;
    }


    @GetMapping(value = "/start", name = "start_simulation_market")
    public Price startSimulationMarket(){
        return _CandleService.selectLastCandle();
    }

//    @GetMapping(value = "/finish", name = "finish_simulation_market")
//    public String finishSimulationMarket(){
//        return _InsertCandleService.insertCandle(price,"1m");
//    }


}


