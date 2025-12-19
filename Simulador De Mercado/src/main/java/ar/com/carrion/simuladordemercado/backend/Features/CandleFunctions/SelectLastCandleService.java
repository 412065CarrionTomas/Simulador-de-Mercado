package ar.com.carrion.simuladordemercado.backend.Features.CandleFunctions;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Price;
import ar.com.carrion.simuladordemercado.backend.Persistence.ICandleRepository;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectLastCandleService {
    private final ICandleRepository _CandleRepository;

    public SelectLastCandleService(ICandleRepository candleRepository) {
        _CandleRepository = candleRepository;
    }
    public String selectLastCandle(Price price){
        List<Candle> priceInBD = _CandleRepository.getLastCandle();
        price.setHighExtremePrice(priceInBD.get(1).getHighExtremePrice());
        price.setLowExtremePrice(priceInBD.get(1).getLowExtremePrice());
        price.setClosePrice(priceInBD.get(1).getClosePrice());
        price.setOpenPrice(priceInBD.get(0).getClosePrice());
        return "Now is start";
    }
}
