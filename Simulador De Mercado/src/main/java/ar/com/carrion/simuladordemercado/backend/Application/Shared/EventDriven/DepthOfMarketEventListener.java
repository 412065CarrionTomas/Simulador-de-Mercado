package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarket;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarketResult;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import org.springframework.context.event.EventListener;

public class DepthOfMarketEventListener {
    private final DepthOfMarketService depthOfMarketService;
    private final DepthOfMarketNotificationService depthOfMarketNotificationService;

    public DepthOfMarketEventListener(DepthOfMarketService depthOfMarketService, DepthOfMarketNotificationService depthOfMarketNotificationService, OrderBookService orderBookService, DepthOfMarket depthOfMarket) {
        this.depthOfMarketService = depthOfMarketService;
        this.depthOfMarketNotificationService = depthOfMarketNotificationService;
    }

    @EventListener
    public void onOrderBookChanged(OrderBookChangedEvent event){
        DepthOfMarketResult dom = depthOfMarketService.getDepthOfMarket();
        depthOfMarketNotificationService.notifyDepthOfMarketUpdate(dom);
    }
}
