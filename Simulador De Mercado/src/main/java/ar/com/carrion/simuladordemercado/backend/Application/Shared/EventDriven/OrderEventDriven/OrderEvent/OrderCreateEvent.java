package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
public class OrderCreateEvent {
    private String eventType;
    private LocalDateTime eventTime;
    private Order order;
    private double executionPrice;
    private boolean fullyExecuted;
}
