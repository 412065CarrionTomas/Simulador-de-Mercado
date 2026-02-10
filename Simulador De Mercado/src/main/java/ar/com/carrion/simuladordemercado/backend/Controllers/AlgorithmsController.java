package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService.RandomAlgorithmService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RestController
public class AlgorithmsController {
    private final RandomAlgorithmService randomAlgorithmService;
    private final OrderBookService orderBookService;

    public AlgorithmsController(RandomAlgorithmService randomAlgorithmService, OrderBookService orderBookService) {
        this.randomAlgorithmService = randomAlgorithmService;
        this.orderBookService = orderBookService;
    }

    @GetMapping("/randomAlgorithm")
    public Map<String, Object> startRandom(){
        randomAlgorithmService.randomAlgorithm();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("bids", orderBookService.getAllBids());
        response.put("asks", orderBookService.getAllAsks());
        response.put("totalBids", orderBookService.getAllBids().size());
        response.put("totalAsks", orderBookService.getAllAsks().size());

        return response;
    }
}

