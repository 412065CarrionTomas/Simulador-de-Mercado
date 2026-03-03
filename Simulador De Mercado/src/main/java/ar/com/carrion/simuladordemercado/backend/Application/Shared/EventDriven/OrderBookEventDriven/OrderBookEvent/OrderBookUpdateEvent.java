package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class OrderBookUpdateEvent {
    private LocalDateTime eventTime;
    private Order order;
    private MatchResult matchResult;

    public OrderBookUpdateEvent(LocalDateTime eventTime, Order order, MatchResult matchResult) {
        this.eventTime = eventTime;
        this.order = order;
        this.matchResult = matchResult;
    }
}
