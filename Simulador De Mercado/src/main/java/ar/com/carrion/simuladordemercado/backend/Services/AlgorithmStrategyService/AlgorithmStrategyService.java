package ar.com.carrion.simuladordemercado.backend.Services.AlgorithmStrategyService;

import ar.com.carrion.simuladordemercado.backend.Algorithm.IAlgorithStrategy;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;

import java.util.List;


public class AlgorithmStrategyService {
    private final List<IAlgorithStrategy> algorithms;

    public AlgorithmStrategyService(List<IAlgorithStrategy> algorithms) {
        this.algorithms = algorithms;
    }

    public Order runAlgorithm(String algorithmName, double currentPrice) {
        return algorithms.stream()
                .filter(algo -> algo.getClass().getSimpleName().equalsIgnoreCase(algorithmName))
                .findFirst()
                .map(algo -> algo.execute(currentPrice))
                .orElseThrow(() -> new IllegalArgumentException("Algorithm not found"));
    }

}
