package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderListener;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderSnapshotService.OrderSnapshotService;
import org.springframework.stereotype.Service;

@Service
public class OrderSnapshotListener {
    private OrderSnapshotService orderSnapshotService;

    public OrderSnapshotListener(OrderSnapshotService orderSnapshotService) {
        this.orderSnapshotService = orderSnapshotService;
    }

}
