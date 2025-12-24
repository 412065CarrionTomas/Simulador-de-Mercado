package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Services.CandleService.CandleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CandleController {

    private final CandleService _CandleService;

    public CandleController(CandleService candleService) {
        _CandleService = candleService;
    }


    @GetMapping("/newPrice/{newPrice}")
    public String ResetCandle(@PathVariable double newPrice){
        _CandleService.resetCandle(newPrice);
        return "Candle reset successfully.";
    }

}
