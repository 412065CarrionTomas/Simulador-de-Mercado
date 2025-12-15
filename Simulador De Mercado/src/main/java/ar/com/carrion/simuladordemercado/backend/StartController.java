package ar.com.carrion.simuladordemercado.backend;
import ar.com.carrion.simuladordemercado.backend.Domains.Prices;
import ar.com.carrion.simuladordemercado.backend.Features.ModifyPrices.ModifyPriceService;
import ar.com.carrion.simuladordemercado.backend.Features.ModifyPrices.ResetPriceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")

public class StartController {

    private final ModifyPriceService _ModifyPriceService;
    private final ResetPriceService _ResetPriceService;

    public StartController(ModifyPriceService modifyPriceService, ResetPriceService resetPriceService) {
        _ModifyPriceService = modifyPriceService;
        _ResetPriceService = resetPriceService;
    }

    @GetMapping("/round/{round}")
    public Prices RandomPrice(@PathVariable int round){
        if(round == 0){
            _ResetPriceService.resetAndSavePrices();
            return null;
        }
        if(round == 1){
            return _ModifyPriceService.getModifyPriceByRandom();
        }
        return null;
    }
//        Prices currentPrices = _PriceRepository.getTablePrices();
//        double newPrice = _RandomAlgorithmService.RandomPrice(currentPrices.getClosePrice());
//        _PriceRepository.updateClosePrice(newPrice);
//        currentPrices.setClosePrice(newPrice);
//        return currentPrices;
//
//    @DeleteMapping
//    public String delete() {
//        _PriceRepository.deleteAll();
//        return "Ok";
//    }
//
//    @PostMapping("/value")
//    public String post(@PathVariable double value) {
//        Prices newEntity = new Prices();
//        newEntity.setOpenPrice(value);
//        newEntity.setClosePrice(value);
//        newEntity.setHighExtremePrice(value);
//        newEntity.setLowExtremePrice(value);
//        _PriceRepository.save(newEntity);
//        return "Ok";
}


