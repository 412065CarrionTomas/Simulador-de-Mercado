package ar.com.carrion.simuladordemercado.backend.Features.ModifyPrices;

import ar.com.carrion.simuladordemercado.backend.Domains.Prices;
import ar.com.carrion.simuladordemercado.backend.Persistence.IPriceRepository;
import ar.com.carrion.simuladordemercado.backend.RandomAlgorithm.RandomAlgorithmService;
import org.springframework.stereotype.Service;


@Service
public class ModifyPriceService {

    private final IPriceRepository _PriceRepository;
    private final RandomAlgorithmService _RandomAlgorithmService;

    public ModifyPriceService(IPriceRepository priceRepository, RandomAlgorithmService randomAlgorithmService) {
        _PriceRepository = priceRepository;
        _RandomAlgorithmService = randomAlgorithmService;
    }

    public Prices getModifyPriceByRandom(){
        Prices currentPrices = _PriceRepository.getTablePrices();

        Prices newPrices = new Prices();
        newPrices.setClosePrice(_RandomAlgorithmService.RandomPrice(currentPrices.getClosePrice()));
        newPrices.setOpenPrice(currentPrices.getOpenPrice());
        newPrices.setHighExtremePrice(currentPrices.getHighExtremePrice());
        newPrices.setLowExtremePrice(currentPrices.getLowExtremePrice());

        modifyPriceExtreme(newPrices);

        _PriceRepository.updateTablePrices(newPrices);

        return _PriceRepository.getTablePrices();
    }

    private void modifyPriceExtreme(Prices lastPrice){
        double currentPrice = lastPrice.getClosePrice();

        if(currentPrice > lastPrice.getHighExtremePrice()){
            lastPrice.setHighExtremePrice(currentPrice);
        }

        if(currentPrice < lastPrice.getLowExtremePrice()){
            lastPrice.setLowExtremePrice(currentPrice);
        }
    }


}

