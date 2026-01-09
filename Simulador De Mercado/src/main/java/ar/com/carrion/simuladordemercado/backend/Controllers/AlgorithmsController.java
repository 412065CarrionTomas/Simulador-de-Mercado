package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService.RandomAlgorithmService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService.CandleService;
import ar.com.carrion.simuladordemercado.backend.Application.Services.OrderBookService.OrderBookService;
import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import ar.com.carrion.simuladordemercado.backend.Domains.OrderBook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ClientInfoStatus;
import java.util.List;

@Controller
@RestController
public class AlgorithmsController {
    private final RandomAlgorithmService randomAlgorithmService;
    private final OrderBookService orderBookService;
    private final Candle candle;

    public AlgorithmsController(RandomAlgorithmService randomAlgorithmService, OrderBookService orderBookService, Candle candle) {
        this.randomAlgorithmService = randomAlgorithmService;
        this.orderBookService = orderBookService;
        this.candle = candle;
    }

    @GetMapping("/randomAlgorithm")
    public void startRandom(){
        randomAlgorithmService.randomAlgorithm();
    }

    @GetMapping("/getAllBids")
    public List<Order> getAllBids(){
        return orderBookService.getAllBids();
    }

    @GetMapping("/getAllAsks")
    public List<Order> getAllAsks(){
        return orderBookService.getAllAsks();
    }
}
