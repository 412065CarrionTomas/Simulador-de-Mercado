package ar.com.carrion.simuladordemercado.backend.Algorithm;


import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Infrastructure.Candle.ICandleDataRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TrendFollowersAlgorithm {

    private final ICandleDataRepository _CandleRepository;

    public TrendFollowersAlgorithm(ICandleDataRepository candleRepository) {
        _CandleRepository = candleRepository;
    }

    //    metodo para algoritmos que siguen tendencia

    public double randomPrice(double price){
        double percentage = 0;
        String trend = trendConfirmation();
        if (Objects.equals(trend, "Bullish")) {
            percentage = (Math.random() * 5);
        }
        else if(Objects.equals(trend,"Bearish")){
            percentage = (Math.random() * -5);
        }
        else if(Objects.equals(trend,"Lateralization")){
            return price;
        }
        double adjusted = price * (1 + percentage / 100);

        double factor = 1000.0;

        return ((long) (adjusted * factor))/factor;
    }

    private String trendConfirmation(){
        List<Candle> threeLastCandles = _CandleRepository.getThreeLastCandles();
        int bullishCount = 0;
        for (Candle candle : threeLastCandles) {
            if(candle.getClosePrice() > candle.getOpenPrice()){
                bullishCount++;
            }
        }
        if(bullishCount == 3){
            return "Bullish";
        }
        else if(bullishCount == 0){
            return "Bearish";
        }
        else{
            return "Lateralization";
        }
    }
}
