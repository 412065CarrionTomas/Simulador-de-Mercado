package ar.com.carrion.simuladordemercado.backend.Infrastructure;

import ar.com.carrion.simuladordemercado.backend.Domains.OrderSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderSnapshotDataRepository extends JpaRepository<OrderSnapshot, Long> {
}
