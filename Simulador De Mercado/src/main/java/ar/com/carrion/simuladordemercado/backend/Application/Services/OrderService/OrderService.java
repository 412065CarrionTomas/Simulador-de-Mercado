package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderService;

import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.IOrderDataRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderBook orderBook;
    private final IOrderDataRepository orderDataRepository;

    public OrderService(OrderBook orderBook, IOrderDataRepository orderDataRepository) {
        this.orderBook = orderBook;
        this.orderDataRepository = orderDataRepository;
    }

    //mover al orderBook
    public void insertOrderBook(){
        //No hace falta, ya que la BD no genera mas sus propios ids.
//        orderBook.getAsks().forEach(order -> order.setId(null));
//        orderBook.getBids().forEach(order -> order.setId(null));

        orderDataRepository.saveAll(orderBook.getAsks());
        orderDataRepository.saveAll(orderBook.getBids());
    }

    public void selectAllOrderBook(){
        orderBook.setBids(orderDataRepository.findByTypeOrder("buy"));
        orderBook.setAsks(orderDataRepository.findByTypeOrder("sell"));
    }

    public void deleteAllOrdersInBD(){
        orderDataRepository.deleteAllOrders();
    }

    public Long getMaxIdOrder(){ return orderDataRepository.findMaxId();}
}
