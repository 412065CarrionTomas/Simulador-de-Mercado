package ar.com.carrion.simuladordemercado.backend.Algorithm;


import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import org.springframework.stereotype.Component;

@Component
public interface IAlgorithStrategy {
    void execute(double currentPrice, OrderBook orderBook);
}
