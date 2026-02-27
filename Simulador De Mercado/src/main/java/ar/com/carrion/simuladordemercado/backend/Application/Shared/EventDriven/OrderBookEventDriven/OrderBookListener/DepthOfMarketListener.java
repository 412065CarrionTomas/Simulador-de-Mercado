package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookListener;


import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarket;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarketResult;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DepthOfMarketListener {
    private final DepthOfMarketService depthOfMarketService;
    private final DepthOfMarketNotificationService depthOfMarketNotificationService;

    public DepthOfMarketListener(DepthOfMarketService depthOfMarketService, DepthOfMarketNotificationService depthOfMarketNotificationService, OrderBookService orderBookService, DepthOfMarket depthOfMarket) {
        this.depthOfMarketService = depthOfMarketService;
        this.depthOfMarketNotificationService = depthOfMarketNotificationService;
    }

    @EventListener
    public void onOrderBookChanged(OrderBookUpdateEvent event){
        DepthOfMarketResult dom = depthOfMarketService.getDepthOfMarket();
        depthOfMarketNotificationService.notifyDepthOfMarketUpdate(dom);
    }
}
