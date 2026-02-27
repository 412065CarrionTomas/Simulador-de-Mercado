package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import lombok.Getter;
import org.springframework.boot.actuate.endpoint.annotation.Selector;

@Getter
public class OrderBookUpdateEvent {
    private MatchResult matchResult;

    public OrderBookUpdateEvent(MatchResult matchResult) {
        this.matchResult = matchResult;
    }
}
