package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderSnapshotService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderSnapshot;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.IOrderSnapshotDataRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderSnapshotService {
    private final IOrderSnapshotDataRepository orderSnapshotDataRepository;

    public OrderSnapshotService(IOrderSnapshotDataRepository orderSnapshotDataRepository) {
        this.orderSnapshotDataRepository = orderSnapshotDataRepository;
    }

    public void saveOrderSnapshot(OrderUpdateEvent event){
        OrderSnapshot orderSnapshot = new OrderSnapshot();
        orderSnapshot.setEventType(selectEvent(event.getMatchResult()));
        orderSnapshot.setCreationOrderTime(event.getOrder().getTime());
        orderSnapshot.setEventTime(event.getEventTime());
        orderSnapshot.setPrice(event.getOrder().getPrice());
        orderSnapshot.setOrderType(event.getOrder().getTypeOrder());
        orderSnapshot.setOriginalQuantity(event.getOrder().getQuantity());
        orderSnapshot.setExecutedQuantity(event.getMatchResult().getQuantityFilled());
        orderSnapshot.setRemainingQuantity(event.getMatchResult().getRemainingQuantity());
        orderSnapshot.setFullyExecuted(event.getMatchResult().isFullyExecuted());
        orderSnapshot.setExecutionPrice(event.getMatchResult().getPriceExecution());
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

}
