package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import java.util.Comparator;
import java.util.List;

public class OrderBookService {

    private final OrderBook orderBook;

    public OrderBookService(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public List<Order> getAllBids(){
        return orderBook.getBids();
    }

    public List<Order> getAllAsks(){
        return orderBook.getAsks();
    }

    public void sortOrderBook() {
        sortBids(orderBook.getBids());
        sortAsks(orderBook.getAsks());
    }

    public void addLimitBuyOrder(Order order) {
        validateOrder(order);

        List<Order> bids = orderBook.getBids();

        for (int i = 0; i < bids.size(); i++) {
            Order current = bids.get(i);
            if (current.getPrice().getValue() < order.getPrice().getValue() ||
                    (current.getPrice().getValue() == order.getPrice().getValue() &&
                            current.getTime().isBefore(order.getTime())
                    )
            ) {
                bids.add(i, order);
                return;
            }
        }
        bids.add(order);
    }

    public void addLimitSellOrder(Order order) {
        validateOrder(order);

        List<Order> asks = orderBook.getAsks();

        for (int i = 0; i < asks.size(); i++) {
            Order current = asks.get(i);
            if(current.getPrice().getValue() > order.getPrice().getValue() ||
                    (current.getPrice().getValue() == order.getPrice().getValue() &&
                            current.getTime().isBefore(order.getTime())
                    )
            ){
                asks.add(i,order);
                return;
            }
        }
        asks.add(order);
    }

    private void sortBids(List<Order> bids) {
        if (bids == null || bids.size() <= 1) {
            return;
        }
        bids.sort(Comparator.comparing(Order::getPrice).reversed()
                .thenComparing(Order::getTime));
    }

    private void sortAsks(List<Order> asks) {
        if (asks == null || asks.size() <= 1) {
            return;
        }
        asks.sort(Comparator.comparing(Order::getPrice)
                .thenComparing(Order::getTime));
    }

    private void validateOrder(Order order){
        if(order == null){
            throw new NullPointerException();
        }
        if(order.getPrice() == null){
            throw new NullPointerException("No puede ingresar Class.Price null. ERROR.");
        }
        if (order.getQuantity() == 0 || order.getPrice().getValue() == 0){
            throw new IllegalArgumentException("Campo cantidad/precio es 0. ERROR.");
        }
        if (!order.getTypeOrder().equals("buy") && !order.getTypeOrder().equals("sell")) {
            throw new IllegalArgumentException("Los tipos de ordenes solo pueden ser buy o sell. ERROR.");
        }
        if(order.getTime() == null){
            throw new IllegalArgumentException("La fecha no puede ser null. ERROR.");
        }
    }
}
