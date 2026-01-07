package ar.com.carrion.simuladordemercado.backend.Infrastructure;


import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDataRepository extends JpaRepository<Order, Long> {
    List<Order> findByTypeOrder(String typeOrder);
}
