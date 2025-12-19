package ar.com.carrion.simuladordemercado.backend.Features.Algorithms.RandomAlgorithm;

import org.springframework.stereotype.Service;


@Service
public class RandomAlgorithmService {
    //Metodo para randomizar el precio

    public double RandomPrice(double price){
        double percentage = (Math.random() * 10) - 5;
        double adjusted = price * (1 + percentage / 100);

        double factor = 1000.0;

        return ((long) (adjusted * factor))/factor;
    }

}