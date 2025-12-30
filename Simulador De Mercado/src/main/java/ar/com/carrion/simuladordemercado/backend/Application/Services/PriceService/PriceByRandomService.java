//package ar.com.carrion.simuladordemercado.backend.Services.PriceService;
//
//import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
//import ar.com.carrion.simuladordemercado.backend.Domains.Price;
//import ar.com.carrion.simuladordemercado.backend.Algorithm.RandomAlgorithm;
//
//
//public class PriceByRandomService {
//
//    private final RandomAlgorithm _RandomAlgorithm;
//    private final Price value;
//    private final OrderBook orderBook;
//
//    public PriceByRandomService(RandomAlgorithm randomAlgorithm, Price value, OrderBook orderBook) {
//        _RandomAlgorithm = randomAlgorithm;
//        this.value = value;
//        this.orderBook = orderBook;
//    }
//
//    public Price ModifyPriceByRandom(){
//        value.setClosePrice(_RandomAlgorithm.RandomPrice(value.getClosePrice()));
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
