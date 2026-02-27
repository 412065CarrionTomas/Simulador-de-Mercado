<<<<<<<< HEAD:Simulador De Mercado/src/main/java/ar/com/carrion/simuladordemercado/backend/Application/Shared/EventDriven/OrderBookListener/DepthOfMarketListener.java
package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookListener;
========
package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookListener;

>>>>>>>> recover-stash:Simulador De Mercado/src/main/java/ar/com/carrion/simuladordemercado/backend/Application/Shared/EventDriven/OrderBookEventDriven/OrderBookListener/DepthOfMarketListener.java

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarket;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarketResult;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService.DepthOfMarketService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
<<<<<<<< HEAD:Simulador De Mercado/src/main/java/ar/com/carrion/simuladordemercado/backend/Application/Shared/EventDriven/OrderBookListener/DepthOfMarketListener.java
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEvents.OrderBookUpdateEvent;
========
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
>>>>>>>> recover-stash:Simulador De Mercado/src/main/java/ar/com/carrion/simuladordemercado/backend/Application/Shared/EventDriven/OrderBookEventDriven/OrderBookListener/DepthOfMarketListener.java
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
