package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Features.CandleFunctions.InsertCandleService;
import ar.com.carrion.simuladordemercado.backend.Features.CandleFunctions.SelectLastCandleService;
import ar.com.carrion.simuladordemercado.backend.Features.Singleton.PriceSingleton;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

@RestController
@RequestMapping("/")

public class StartController {

    private final SelectLastCandleService _SelectLastCandleService;
    private final InsertCandleService _InsertCandleService;
    Price price = PriceSingleton.getInstance();

    public StartController(SelectLastCandleService selectLastCandleService, InsertCandleService insertCandleService) {
        _SelectLastCandleService = selectLastCandleService;
        _InsertCandleService = insertCandleService;
    }

    @GetMapping(value = "/start", name = "start_simulation_market")
    public String startSimulationMarket(){
        return _SelectLastCandleService.selectLastCandle(price);
    }

    @GetMapping(value = "/finish", name = "finish_simulation_market")
    public String finishSimulationMarket(){
        return _InsertCandleService.insertCandle(price,"1m");
    }


}


