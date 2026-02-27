package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookListener;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEvents.OrderBookUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderBookListener {
    private final OrderBookService orderBookService;
    private final OrderBookNotificationService orderBookNotificationService;

    public OrderBookListener(OrderBookService orderBookService, OrderBookNotificationService orderBookNotificationService) {
        this.orderBookService = orderBookService;
        this.orderBookNotificationService = orderBookNotificationService;
    }

    @EventListener
    public void onOrderBookChanged(OrderBookUpdateEvent event){
        orderBookNotificationService.notifyOrderBookUpdate(
                orderBookService.getAllBids(),
                orderBookService.getAllAsks());
    }
}
