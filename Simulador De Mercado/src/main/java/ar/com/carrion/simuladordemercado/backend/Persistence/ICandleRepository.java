package ar.com.carrion.simuladordemercado.backend.Persistence;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ICandleRepository extends JpaRepository<Candle, Long> {

    @Transactional
    @Modifying
    @Query("update Candle set closePrice = :newPrice")
    void updateClosePrice(double newPrice);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO candle (time_frame, time_close, open_price, close_price, low_extreme_price, high_extreme_price) " +
           "VALUES (:#{#candle.timeFrame}, :#{#candle.timeClose}, :#{#candle.openPrice}, :#{#candle.closePrice}, :#{#candle.lowExtremePrice}, " +
            ":#{#candle.highExtremePrice})", nativeQuery = true)
    void insertCandle(Candle candle);

    @Query("SELECT c FROM Candle c order by c.timeClose DESC LIMIT 2")
    List<Candle> getLastCandle();

    @Query("SELECT c FROM Candle c order by c.timeClose DESC LIMIT 3")
    List<Candle> getThreeLastCandles();
}