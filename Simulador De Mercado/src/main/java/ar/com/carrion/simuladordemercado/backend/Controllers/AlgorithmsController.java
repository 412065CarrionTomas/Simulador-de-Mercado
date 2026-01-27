package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService.RandomAlgorithmService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookNotificationService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ClientInfoStatus;
import java.util.List;

@Controller
@RestController
public class AlgorithmsController {
    private final RandomAlgorithmService randomAlgorithmService;

    public AlgorithmsController(RandomAlgorithmService randomAlgorithmService) {
        this.randomAlgorithmService = randomAlgorithmService;
    }

    @GetMapping("/randomAlgorithm")
    public void startRandom(){
        randomAlgorithmService.randomAlgorithm();
    }



}
