package ar.com.carrion.simuladordemercado.backend.Features.CandleFunctions;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Persistence.ICandleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InsertCandleService {
    private final ICandleRepository _CandleRepository;

    public InsertCandleService(ICandleRepository candleRepository) {
        _CandleRepository = candleRepository;
    }

    public String insertCandle(Price price, String timeFrame){
        Candle newCandle = new Candle();
        newCandle.setOpenPrice(price.getOpenPrice());
        newCandle.setClosePrice(price.getClosePrice());
        newCandle.setHighExtremePrice(price.getHighExtremePrice());
        newCandle.setLowExtremePrice(price.getLowExtremePrice());
        newCandle.setTimeFrame(timeFrame);
        newCandle.setTimeClose(LocalDateTime.now());

        _CandleRepository.insertCandle(newCandle);
        return "Market Finish.";
    }
}
