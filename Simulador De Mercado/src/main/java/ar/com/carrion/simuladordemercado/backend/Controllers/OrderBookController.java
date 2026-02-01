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

    /*
    * Todavia sin terminar logica. Complicacion con los id autogenerados por spring.
    * ID suplanta a ID eliminado, genera error.
    *  */

//    @GetMapping("/inserAllOrdersInOrderBook")
//    public void insertAllOrdersInBook(){
//        orderService.insertOrderBook();
//    }

}
