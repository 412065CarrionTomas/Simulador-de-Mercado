package ar.com.carrion.simuladordemercado.backend.Application.Services.OrderSnapshotService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.MatchResult;
import ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine.OrderResult;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderCreateEvent;
import ar.com.carrion.simuladordemercado.backend.Application.Shared.EventDriven.OrderEventDriven.OrderEvent.OrderUpdateEvent;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderSnapshot;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.IOrderSnapshotDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderSnapshotService {
    private final IOrderSnapshotDataRepository orderSnapshotDataRepository;

    public OrderSnapshotService(IOrderSnapshotDataRepository orderSnapshotDataRepository) {
        this.orderSnapshotDataRepository = orderSnapshotDataRepository;
    }

    public void saveOrderSnapshot(OrderUpdateEvent event){
        List<OrderSnapshot> orderSnapshotList = new ArrayList<>();

        for (OrderResult orderResult : event.getMatchResult().getMainOrdersModified()){
            getOrdersSnapshots(event, orderResult, orderSnapshotList);
        }

        for (OrderResult orderResult : event.getMatchResult().getOppositeOrdersModified()){
            getOrdersSnapshots(event, orderResult, orderSnapshotList);
        }

        orderSnapshotDataRepository.saveAll(orderSnapshotList);
    }

    private void getOrdersSnapshots(OrderUpdateEvent event, OrderResult orderResult, List<OrderSnapshot> orderSnapshotList) {
        OrderSnapshot orderSnapshot = new OrderSnapshot();
        orderSnapshot.setEventType(selectEvent(orderResult));
        orderSnapshot.setEventTime(event.getEventTime());
        selectPropertisInOrderToOrderSnapshot(orderSnapshot, event.getMatchResult().getMainOrder(), orderResult);
        selectPropertisInMatchResultToOrderSnapshot(orderSnapshot, orderResult);
        orderSnapshot.setIdOrderOpposite(orderResult.oppositeIdOrder());
        orderSnapshotList.add(orderSnapshot);
    }


    public void saveOrderSnapshotEventTypeCreated(OrderCreateEvent event){
        OrderSnapshot orderSnapshot = new OrderSnapshot();
        orderSnapshot.setEventType(event.getTypeEvent());
        orderSnapshot.setEventTime(event.getEventTime());
        orderSnapshot.setIdOrderOpposite(null);
        selectPropertisInOrderToOrderSnapshot(orderSnapshot, event.getOrder(), null);
        selectPropertisInMatchResultToOrderSnapshot(orderSnapshot, null);
        orderSnapshot.setRemainingQuantity(event.getOrder().getQuantity());

        orderSnapshotDataRepository.save(orderSnapshot);

    }

    /**/

    /* REVISAR PROBLEMA DE NULL*/

    /**/
    private void selectPropertisInOrderToOrderSnapshot(OrderSnapshot orderSnapshot, Order mainOrder, OrderResult orderResult){
        orderSnapshot.setIdOrder(mainOrder.getId());
        orderSnapshot.setCreationOrderTime(mainOrder.getTime());
        orderSnapshot.setPrice(mainOrder.getPrice());
        orderSnapshot.setOrderType(mainOrder.getTypeOrder());
        orderSnapshot.setQuantity(mainOrder.getQuantity());
        if (orderResult == null){
            orderSnapshot.setQuantity(orderResult.initialQuantity());
        }
    }

    private void selectPropertisInMatchResultToOrderSnapshot(OrderSnapshot orderSnapshot, OrderResult orderResult){
        int filledQty = (orderResult == null) ? 0 : orderResult.quantityFilled();
        int remainingQty = (orderResult == null) ? 0 : orderResult.remainingQuantity();
        boolean fullyEx = orderResult != null && orderResult.fullyExecuted();
        double exPrice = (orderResult == null) ? 0 : orderResult.priceExecution();

        orderSnapshot.setFilledQuantity(filledQty);
        orderSnapshot.setRemainingQuantity(remainingQty);
        orderSnapshot.setFullyExecuted(fullyEx);
        orderSnapshot.setExecutionPrice(exPrice);
    }


    private String selectEvent(OrderResult orderResult){
            if (orderResult.priceExecution() == 0 && !orderResult.fullyExecuted()){
                return "PENDING";
            }
            else if(orderResult.priceExecution() != 0 && !orderResult.fullyExecuted()){
                return "PARTIALLY_FILLED";
            }
            else {
                return "FILLED";
            }
        }
    }
