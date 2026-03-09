package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderListener;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderSnapshotService.OrderSnapshotService;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderCreateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderListener {
    private final OrderSnapshotService orderSnapshotService;

    public OrderListener(OrderSnapshotService orderSnapshotService) {
        this.orderSnapshotService = orderSnapshotService;
    }

    @EventListener
    public void onOrderCreateEvent(OrderCreateEvent event){
        orderSnapshotService.saveOrderSnapshotEventTypeCreated(event);
    }

    @EventListener
    public void onOrderUpdateEvent(OrderUpdateEvent event){
        orderSnapshotService.saveOrderSnapshot(event);
    }

}
