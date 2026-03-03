package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderSnapshotService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderBookEventDriven.OrderBookEvent.OrderBookUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderCreateEvent;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderSnapshot;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.IOrderSnapshotDataRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderSnapshotService {
    private final IOrderSnapshotDataRepository orderSnapshotDataRepository;

    public OrderSnapshotService(IOrderSnapshotDataRepository orderSnapshotDataRepository) {
        this.orderSnapshotDataRepository = orderSnapshotDataRepository;
    }

    public void saveOrderSnapshot(OrderBookUpdateEvent event){
        OrderSnapshot orderSnapshot = new OrderSnapshot();
        orderSnapshot.setEventType(selectEvent(event.getMatchResult()));
        orderSnapshot.setEventTime(event.getEventTime());
        selectPropertisInOrderToOrderSnapshot(orderSnapshot,event.getOrder());
        selectPropertisInMatchResultToOrderSnapshot(orderSnapshot,event.getMatchResult());

        orderSnapshotDataRepository.save(orderSnapshot);
    }

    public void saveOrderSnapshotEventTypeCreated(OrderCreateEvent event){
        OrderSnapshot orderSnapshot = new OrderSnapshot();
        orderSnapshot.setEventType(event.getTypeEvent());
        orderSnapshot.setEventTime(event.getEventTime());
        selectPropertisInOrderToOrderSnapshot(orderSnapshot, event.getOrder());
        MatchResult matchResultWhitDefaultValues = new MatchResult();
        selectPropertisInMatchResultToOrderSnapshot(orderSnapshot, matchResultWhitDefaultValues);
        orderSnapshot.setRemainingQuantity(event.getOrder().getQuantity());

        orderSnapshotDataRepository.save(orderSnapshot);

    }

    private String selectEvent(MatchResult matchResult){
        if (matchResult.getPriceExecution() == 0 && !matchResult.isFullyExecuted()){
            return "PENDING";
        }
        else if(matchResult.getPriceExecution() != 0 && !matchResult.isFullyExecuted()){
            return "FILLED";
        }
        else {
            return "COMPLETE_FILLED";
        }
    }

    private OrderSnapshot selectPropertisInOrderToOrderSnapshot(OrderSnapshot orderSnapshot , Order order){
        orderSnapshot.setCreationOrderTime(order.getTime());
        orderSnapshot.setPrice(order.getPrice());
        orderSnapshot.setOrderType(order.getTypeOrder());
        orderSnapshot.setOriginalQuantity(order.getQuantity());
        return orderSnapshot;
    }

    private OrderSnapshot selectPropertisInMatchResultToOrderSnapshot(OrderSnapshot orderSnapshot, MatchResult matchResult){
        orderSnapshot.setExecutedQuantity(matchResult.getQuantityFilled());
        orderSnapshot.setRemainingQuantity(matchResult.getRemainingQuantity());
        orderSnapshot.setFullyExecuted(matchResult.isFullyExecuted());
        orderSnapshot.setExecutionPrice(matchResult.getPriceExecution());
        return orderSnapshot;
    }

}
