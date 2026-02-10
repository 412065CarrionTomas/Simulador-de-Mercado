package ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarket;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarketResult;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import org.springframework.stereotype.Service;

@Service
public class DepthOfMarketService {
    private final DepthOfMarket depthOfMarket;
    private final OrderBookService orderBookService;

    public DepthOfMarketService(DepthOfMarket depthOfMarket, OrderBookService orderBookService) {
        this.depthOfMarket = depthOfMarket;
        this.orderBookService = orderBookService;
    }

    public DepthOfMarketResult getDepthOfMarket(){
        return depthOfMarket.GetDeptOfMarket(orderBookService.getAllBids(),orderBookService.getAllAsks());
    }

}
