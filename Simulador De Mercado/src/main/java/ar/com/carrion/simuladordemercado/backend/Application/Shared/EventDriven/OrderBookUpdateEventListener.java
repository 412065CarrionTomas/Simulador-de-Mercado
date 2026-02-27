package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
<<<<<<<< Updated upstream:Simulador De Mercado/src/main/java/ar/com/carrion/simuladordemercado/backend/Application/Shared/EventDriven/OrderBookUpdateEventListener.java
========
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
>>>>>>>> Stashed changes:Simulador De Mercado/src/main/java/ar/com/carrion/simuladordemercado/backend/Application/Shared/EventDriven/OrderBookEventDriven/OrderBookListener/OrderBookListener.java
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderBookUpdateEventListener {
    private final OrderBookService orderBookService;
    private final OrderBookNotificationService orderBookNotificationService;

    public OrderBookUpdateEventListener(OrderBookService orderBookService, OrderBookNotificationService orderBookNotificationService) {
        this.orderBookService = orderBookService;
        this.orderBookNotificationService = orderBookNotificationService;
    }

    @EventListener
    public void onOrderBookChanged(OrderBookChangedEvent event){
        orderBookNotificationService.notifyOrderBookUpdate(
                orderBookService.getAllBids(),
                orderBookService.getAllAsks());
    }
}
