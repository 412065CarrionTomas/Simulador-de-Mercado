package ar.com.carrion.simuladordemercado.backend.Infrastructure;


import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderDataRepository extends JpaRepository<Order, Long> {
    List<Order> findByTypeOrder(String typeOrder);

    @Transactional
    @Modifying
    @Query("DELETE FROM Order")
    void deleteAllOrders();
}
