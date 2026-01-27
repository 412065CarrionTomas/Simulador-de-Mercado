package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderService.OrderService;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Controller
public class OrderBookController {
    private final OrderService orderService;
    private final OrderBookService orderBookService;

    public OrderBookController(OrderService orderService, OrderBookService orderBookService) {
        this.orderService = orderService;
        this.orderBookService = orderBookService;
    }

    @GetMapping("/inserAllOrdersInOrderBook")
    public String insertAllOrdersInBook(){
        orderService.insertOrderBook();
        return "MarketFinish";
    }

//
//    @MessageMapping("/orders-bids")
//    @SendTo("/topic/bids")
//    public List<Order> getAllBids(){
//        return orderBookService.getAllBids();
//    }
//
//    @MessageMapping("/orders-asks")
//    @SendTo("/topic/asks")
//    public List<Order> getAllAsks(){
//        return orderBookService.getAllAsks();
//    }
}
