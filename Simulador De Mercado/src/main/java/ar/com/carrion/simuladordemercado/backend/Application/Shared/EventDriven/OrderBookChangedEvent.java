package ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven;

public class OrderBookChangedEvent {
    private final String reason;

    public  OrderBookChangedEvent(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return reason;
    }

}
