//package ar.com.carrion.simuladordemercado.backend.Services.PriceService;
//
//import ar.com.carrion.simuladordemercado.backend.Domains.Price;
//import ar.com.carrion.simuladordemercado.backend.Algorithm.TrendFollowersAlgorithm;
//
//
//public class PriceByTrendFollowerService {
//    private final TrendFollowersAlgorithm _TrendFollowersAlgorithm;
//    private final Price value;
//
//    public PriceByTrendFollowerService(TrendFollowersAlgorithm trendFollowersAlgorithm, Price value) {
//        _TrendFollowersAlgorithm = trendFollowersAlgorithm;
//        this.value = value;
//    }
//
//    public Price modifyPriceByTrendFollower(){
//        value.setClosePrice(_TrendFollowersAlgorithm.randomPrice(value.getClosePrice()));
//        modifyPriceExtreme(value);
//        return value;
//    }
//
//    private void modifyPriceExtreme(Price lastPrice){
//        double currentPrice = lastPrice.getClosePrice();
//
//        if(currentPrice > lastPrice.getHighExtremePrice()){
//            lastPrice.setHighExtremePrice(currentPrice);
//        }
//
//        if(currentPrice < lastPrice.getLowExtremePrice()){
//            lastPrice.setLowExtremePrice(currentPrice);
//        }
//    }
//}
