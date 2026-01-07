package ar.com.carrion.simuladordemercado.backend.Domains;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ORDER BOOK ===\n");

        sb.append("\n--- BIDS (Compra) ---\n");
        if (bids.isEmpty()) {
            sb.append("  (vacío)\n");
        } else {
            bids.forEach(bid ->
                    sb.append("  ").append(bid.toString()).append("\n")
            );
        }

        sb.append("\n--- ASKS (Venta) ---\n");
        if (asks.isEmpty()) {
            sb.append("  (vacío)\n");
        } else {
            asks.forEach(ask ->
                    sb.append("  ").append(ask.toString()).append("\n")
            );
        }

        sb.append("==================");
        return sb.toString();
    }
}
