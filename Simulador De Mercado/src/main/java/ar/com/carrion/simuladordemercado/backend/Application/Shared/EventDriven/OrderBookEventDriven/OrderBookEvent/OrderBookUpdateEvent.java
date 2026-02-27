package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDrive.OrderBookEvent;

import lombok.Getter;

@Getter
public class OrderBookUpdateEvent {
    private final String reason;

    public OrderBookUpdateEvent(String reason){
        this.reason = reason;
    }
}
