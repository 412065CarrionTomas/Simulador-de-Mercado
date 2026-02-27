package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Getter
public class OrderUpdateEvent {
    private LocalDateTime eventTime;
    private Order order;
    private MatchResult matchResult;

    public OrderUpdateEvent(Order order, MatchResult matchResult){
        this.eventTime = LocalDateTime.now(ZoneOffset.UTC);
        this.order = order;
        this.matchResult = matchResult;
    }
}
