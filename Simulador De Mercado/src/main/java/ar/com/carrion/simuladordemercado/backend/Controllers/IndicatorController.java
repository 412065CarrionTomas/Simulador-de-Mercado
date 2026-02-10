package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarketResult;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class IndicatorController {
    private final DepthOfMarketService depthOfMarketService;

    public IndicatorController(DepthOfMarketService depthOfMarketService) {
        this.depthOfMarketService = depthOfMarketService;
    }

    @GetMapping("/getDOM")
    public DepthOfMarketResult getDepthOfMarket(){
        return depthOfMarketService.getDepthOfMarket();
    }
}
