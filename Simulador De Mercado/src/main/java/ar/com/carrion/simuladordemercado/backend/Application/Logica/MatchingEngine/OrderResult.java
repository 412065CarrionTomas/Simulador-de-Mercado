package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;

import java.time.LocalDateTime;

public record OrderResult(
        Long id,
        double price,
        int initialQuantity, //cantidad incial con la que comenzo el evento
        double priceExecution,
        boolean fullyExecuted,
        int quantityFilled,
        int remainingQuantity,
        Long oppositeIdOrder
){}
