package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
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
