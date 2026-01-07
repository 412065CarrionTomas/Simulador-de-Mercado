package ar.com.carrion.simuladordemercado.backend.Controllers;

import ar.com.carrion.simuladordemercado.backend.Application.Services.AlgorithmService.RandomAlgorithmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class AlgorithmsController {
    private final RandomAlgorithmService randomAlgorithmService;

    public AlgorithmsController(RandomAlgorithmService randomAlgorithmService) {
        this.randomAlgorithmService = randomAlgorithmService;
    }

    @GetMapping(name = "randomAlgorithm")
    public void startRandom(){
        randomAlgorithmService.randomAlgorithm();
    }
}
