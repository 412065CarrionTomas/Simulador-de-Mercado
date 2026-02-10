package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class OrderBookChangedEvent {
    private final String reason;

    public  OrderBookChangedEvent(String reason){
        this.reason = reason;
    }
}
