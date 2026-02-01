package ar.com.carrion.simuladordemercado.backend.Infrastructure;


import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderDataRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.typeOrder = :typeOrder")
    List<Order> findByTypeOrder(@Param("typeOrder") String typeOrder);

    @Transactional
    @Modifying
    @Query("DELETE FROM Order")
    void deleteAllOrders();
}
