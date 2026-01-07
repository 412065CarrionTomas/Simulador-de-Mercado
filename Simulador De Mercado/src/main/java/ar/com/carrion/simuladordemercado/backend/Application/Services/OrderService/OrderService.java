package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderService;

import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.IOrderDataRepository;

public class OrderService {
    private final OrderBook orderBook;
    private final IOrderDataRepository orderDataRepository;

    public OrderService(OrderBook orderBook, IOrderDataRepository orderDataRepository) {
        this.orderBook = orderBook;
        this.orderDataRepository = orderDataRepository;
    }

    public void insertOrderBook(){
        orderDataRepository.saveAll(orderBook.getAsks());
        orderDataRepository.saveAll(orderBook.getBids());
    }

    public void selectAllOrderBook(){
        orderBook.setBids(orderDataRepository.findByTypeOrder("Buy"));
        orderBook.setAsks(orderDataRepository.findByTypeOrder("Sell"));
        orderDataRepository.deleteAll();
    }

}
