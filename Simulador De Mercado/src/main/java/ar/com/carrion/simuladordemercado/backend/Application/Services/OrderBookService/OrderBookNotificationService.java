package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.List;

public class OrderBookNotificationService {
    private final SimpMessagingTemplate template;

    public OrderBookNotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void notifyOrderBookUpdate(List<Order> listBids, List<Order> listAsks){
        template.convertAndSend("/orders/bids", listBids);
        template.convertAndSend("/orders/asks", listAsks);
    }

}
