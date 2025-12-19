package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Features.Algorithms.RandomAlgorithm.PriceByRandomService;
import ar.com.carrion.simuladordemercado.backend.Features.Algorithms.TrendFollowersAlgorithm.PriceByTrendFollowerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AlgorithmsController {

    private final PriceByRandomService _PriceByRandomService;
    private final PriceByTrendFollowerService _PriceByTrendFollowerService;

    public AlgorithmsController(PriceByRandomService priceByRandomService, PriceByTrendFollowerService priceByTrendFollowerService) {
        _PriceByRandomService = priceByRandomService;
        _PriceByTrendFollowerService = priceByTrendFollowerService;
    }

    @GetMapping(value = "/random", name = "modify_price_by_random")
    public Price randomPrice(){
        return _PriceByRandomService.ModifyPriceByRandom();
    }

    @GetMapping(value = "/trenFollower", name = "modify_price_by_trend_follower")
    public Price trendFollowerPrice(){
        return _PriceByTrendFollowerService.modifyPriceByTrendFollower();
    }


}
