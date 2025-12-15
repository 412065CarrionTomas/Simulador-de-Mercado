package ar.com.carrion.simuladordemercado.backend.Persistence;

import ar.com.carrion.simuladordemercado.backend.Domains.Prices;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPriceRepository extends JpaRepository<Prices, Long> {

    @Transactional
    @Modifying
    @Query("update Prices set closePrice = :newPrice")
    void updateClosePrice(double newPrice);


    @Query("SELECT p FROM Prices p WHERE p.id = 1")
    Prices getTablePrices();

    @Transactional
    @Modifying
    @Query("update Prices p set p.openPrice = :#{#newTablePrices.openPrice}, p.closePrice = :#{#newTablePrices.closePrice}, p.highExtremePrice = :#{#newTablePrices.highExtremePrice}, p.lowExtremePrice = :#{#newTablePrices.lowExtremePrice} where p.id = 1")
    void updateTablePrices(Prices newTablePrices);
}