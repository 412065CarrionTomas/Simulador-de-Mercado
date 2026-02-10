package ar.com.carrion.simuladordemercado.backend.Domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class OrderBook {

    private List<Order> bids;
    private List<Order> asks;

    public OrderBook() {
        this.bids = new ArrayList<>();
        this.asks = new ArrayList<>();
    }

    public OrderBook(List<Order> bids, List<Order> asks) {
        this.bids = bids != null ? bids : new ArrayList<>();
        this.asks = asks != null ? asks : new ArrayList<>();
    }
}
