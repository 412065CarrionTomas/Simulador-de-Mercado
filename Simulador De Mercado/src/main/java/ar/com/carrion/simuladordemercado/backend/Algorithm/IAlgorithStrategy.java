package ar.com.carrion.simuladordemercado.backend.Algorithm;


import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import org.springframework.stereotype.Component;

@Component
public interface IAlgorithStrategy {
    Order execute(double price);
}
