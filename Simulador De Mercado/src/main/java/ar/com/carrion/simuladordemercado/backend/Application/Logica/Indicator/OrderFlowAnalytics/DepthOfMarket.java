package ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;

import java.util.*;

public class DepthOfMarket {

    public DepthOfMarketResult GetDeptOfMarket(List<Order> listBids, List<Order> listAsks){
        Map<Double, Integer> bidMap = new LinkedHashMap<>();
        Map<Double, Integer> askMap = new LinkedHashMap<>();


        for(Order bid : listBids){
            bidMap.merge(bid.getPrice(), bid.getQuantity(), Integer::sum);
        }

        for(Order ask : listAsks){
            askMap.merge(ask.getPrice(), ask.getQuantity(), Integer::sum);
        }


        List<Integer> bidSizes = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        List<Integer> askSizes = new ArrayList<>();

        for(Map.Entry<Double, Integer> entry : askMap.entrySet()){
            prices.add(entry.getKey());
            bidSizes.add(0);
            askSizes.add(entry.getValue());
        }

        Collections.reverse(prices);
        Collections.reverse(askSizes);

        for(Map.Entry<Double, Integer> entry : bidMap.entrySet()){
            prices.add(entry.getKey());
            bidSizes.add(entry.getValue());
            askSizes.add(0);
        }

        DepthOfMarketResult DOM = new DepthOfMarketResult();
        DOM.setBidSize(bidSizes);
        DOM.setPrice(prices);
        DOM.setAskSize(askSizes);

        return DOM;
    }

}
