package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Features.CandleFunctions.ResetCandleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CandleController {

    private final ResetCandleService _ResetCandleService;

    public CandleController(ResetCandleService resetCandleService) {
        _ResetCandleService = resetCandleService;
    }

    @GetMapping("/newPrice/{newPrice}")
    public String ResetCandle(@PathVariable double newPrice){
        _ResetCandleService.resetCandle(newPrice);
        return "Candle reset successfully.";
    }

}
