package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderSnapshotService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderCreateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderSnapshot;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.IOrderSnapshotDataRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderSnapshotService {
    private final IOrderSnapshotDataRepository orderSnapshotDataRepository;

    public OrderSnapshotService(IOrderSnapshotDataRepository orderSnapshotDataRepository) {
        this.orderSnapshotDataRepository = orderSnapshotDataRepository;
    }

    public void saveOrderSnapshot(OrderUpdateEvent event){
        List<OrderSnapshot> orderSnapshotList = new ArrayList<>();

        for(Map.Entry<Order, String> orders : event.getMatchResult().getOrders().entrySet()){
            OrderSnapshot orderSnapshot = new OrderSnapshot();
            orderSnapshot.setEventType(orders.getValue());
            orderSnapshot.setEventTime(event.getEventTime());
            selectPropertisInOrderToOrderSnapshot(orderSnapshot,event.getMatchResult().getOrder());
            selectPropertisInMatchResultToOrderSnapshot(orderSnapshot,event.getMatchResult());
            orderSnapshotList.add(orderSnapshot);
        }

        orderSnapshotDataRepository.saveAll(orderSnapshotList);
    }

    public void saveOrderSnapshotEventTypeCreated(OrderCreateEvent event){
        OrderSnapshot orderSnapshot = new OrderSnapshot();
        orderSnapshot.setEventType(event.getTypeEvent());
        orderSnapshot.setEventTime(event.getEventTime());
//        orderSnapshot.setCreationOrderOppositeTime(null);
        selectPropertisInOrderToOrderSnapshot(orderSnapshot, event.getOrder());
        MatchResult matchResultWhitDefaultValues = new MatchResult();
        selectPropertisInMatchResultToOrderSnapshot(orderSnapshot, matchResultWhitDefaultValues);
        orderSnapshot.setRemainingQuantity(event.getOrder().getQuantity());

        orderSnapshotDataRepository.save(orderSnapshot);

    }

    private void selectPropertisInOrderToOrderSnapshot(OrderSnapshot orderSnapshot , Order order){
        orderSnapshot.setCreationOrderTime(order.getTime());
        orderSnapshot.setPrice(order.getPrice());
        orderSnapshot.setOrderType(order.getTypeOrder());
        orderSnapshot.setOriginalQuantity(order.getQuantity());
    }

    private void selectPropertisInMatchResultToOrderSnapshot(OrderSnapshot orderSnapshot, MatchResult matchResult){
        orderSnapshot.setExecutedQuantity(matchResult.getQuantityFilled());
        orderSnapshot.setRemainingQuantity(matchResult.getRemainingQuantity());
        orderSnapshot.setFullyExecuted(matchResult.isFullyExecuted());
        orderSnapshot.setExecutionPrice(matchResult.getPriceExecution());
    }

}

//private String selectEvent(MatchResult matchResult){
//        if (matchResult.getPriceExecution() == 0 && !matchResult.isFullyExecuted()){
//            return "PENDING";
//        }
//        else if(matchResult.getPriceExecution() != 0 && !matchResult.isFullyExecuted()){
//            return "PARTIALLY_FILLED";
//        }
//        else {
//            return "FILLED";
//        }
//    }