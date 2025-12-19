package ar.com.carrion.simuladordemercado.backend.Features.CandleFunctions;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Persistence.ICandleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResetCandleService {
    private final ICandleRepository _CandleRepository;

    public ResetCandleService(ICandleRepository candleRepository) {
        _CandleRepository = candleRepository;
    }

    public void resetCandle(double price){
        Candle candle = new Candle();
        candle.setClosePrice(price);
        candle.setOpenPrice(price);
        candle.setHighExtremePrice(price);
        candle.setLowExtremePrice(price);
        candle.setTimeClose(LocalDateTime.now());
        candle.setTimeFrame("1m");

        _CandleRepository.save(candle);
    }
}
