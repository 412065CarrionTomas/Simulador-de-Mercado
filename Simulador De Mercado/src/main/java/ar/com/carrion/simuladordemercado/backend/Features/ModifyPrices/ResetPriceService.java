package ar.com.carrion.simuladordemercado.backend.Features.ModifyPrices;


import ar.com.carrion.simuladordemercado.backend.Domains.Prices;
import ar.com.carrion.simuladordemercado.backend.Persistence.IPriceRepository;
import org.springframework.stereotype.Service;

@Service
public class ResetPriceService {

    private final IPriceRepository _PriceRepository;

    public ResetPriceService(IPriceRepository priceRepository) {
        _PriceRepository = priceRepository;
    }

    public void resetAndSavePrices(){
        Prices currentPrices = _PriceRepository.getTablePrices();

        currentPrices.setOpenPrice(currentPrices.getClosePrice());
        currentPrices.setLowExtremePrice(currentPrices.getClosePrice());
        currentPrices.setHighExtremePrice(currentPrices.getClosePrice());

        _PriceRepository.updateTablePrices(currentPrices);
    }
}
