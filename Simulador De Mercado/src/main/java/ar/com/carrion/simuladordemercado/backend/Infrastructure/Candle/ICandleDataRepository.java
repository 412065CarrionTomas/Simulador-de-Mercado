package ar.com.carrion.simuladordemercado.backend.Infrastructure.Candle;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ICandleDataRepository extends JpaRepository<Candle, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO candle (time_frame, time_close, open_price, close_price, low_extreme_price, high_extreme_price) " +
           "VALUES (:#{#candle.timeFrame}, :#{#candle.timeClose}, :#{#candle.openPrice}, :#{#candle.closePrice}, :#{#candle.lowExtremePrice}, " +
            ":#{#candle.highExtremePrice})", nativeQuery = true)
    void insertCandle(Candle candle);

    @Query("SELECT c FROM Candle c order by c.timeClose DESC LIMIT 2")
    List<Candle> getTwoLastCandle();

    @Transactional
    default void insertTwoCandles(Candle candle1, Candle candle2) {
        insertCandle(candle1);
        insertCandle(candle2);
    }


}