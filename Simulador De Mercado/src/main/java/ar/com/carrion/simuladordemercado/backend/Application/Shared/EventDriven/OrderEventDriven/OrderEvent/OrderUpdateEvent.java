package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderUpdateEvent {
    private MatchResult matchResult;
    private LocalDateTime eventTime;
}
