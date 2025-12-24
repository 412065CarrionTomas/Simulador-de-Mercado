package ar.com.carrion.simuladordemercado.backend.Services.OrderBookService;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import java.util.Comparator;
import java.util.List;

public class OrderBookService {

    private final OrderBook orderBook;

    public OrderBookService(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void sortOrderBook() {
        sortBids(orderBook.getBids());
        sortAsks(orderBook.getAsks());
    }

    public void addBuyOrder(Order order) {
        List<Order> bids = orderBook.getBids();
        for (int i = 0; i < bids.size(); i++) {
            if (bids.get(i).getPrice() == order.getPrice()){
                bids.get(i).setQuantity(bids.get(i).getQuantity() + order.getQuantity());
                return;
            }
            if(bids.get(i).getPrice() < order.getPrice()){
                bids.add(i, order);
                return;
            }
        }
        bids.add(order);
    }

    public void addSellOrder(Order order) {
        List<Order> asks = orderBook.getAsks();
        for (int i = 0; i < asks.size(); i++) {
            if (asks.get(i).getPrice() == order.getPrice()){
                asks.get(i).setQuantity(asks.get(i).getQuantity() + order.getQuantity());
                return;
            }
            if(asks.get(i).getPrice() > order.getPrice()){
                asks.add(i, order);
                return;
            }
        }
        asks.add(order);
    }

    private void sortBids(List<Order> bids) {
        if (bids == null || bids.size() <= 1) {
            return;
        }
        // Ordenar de mayor a menor precio (descendente)
        bids.sort(Comparator.comparingDouble(Order::getPrice).reversed());
    }

    private void sortAsks(List<Order> asks) {
        if (asks == null || asks.size() <= 1) {
            return;
        }
        // Ordenar de menor a mayor precio (ascendente)
        asks.sort(Comparator.comparingDouble(Order::getPrice));
    }
}
