package ar.com.carrion.simuladordemercado.backend.Application.Services.CandleService;

import ar.com.carrion.simuladordemercado.backend.Domains.Candle;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CandleNotificationService {
    private final SimpMessagingTemplate template;

    public CandleNotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void notifyCandleUpdate(Candle candle){
        template.convertAndSend("/topic/candle", candle);
    }
}
