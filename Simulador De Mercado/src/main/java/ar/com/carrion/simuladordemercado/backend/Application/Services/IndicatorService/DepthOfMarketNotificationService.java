package ar.com.carrion.simuladordemercado.backend.Application.Services.IndicatorService;

import ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics.DepthOfMarketResult;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class DepthOfMarketNotificationService {
    private final SimpMessagingTemplate template;

    public DepthOfMarketNotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void notifyDepthOfMarketUpdate(DepthOfMarketResult data){
        template.convertAndSend("/topic/indicador/DOM", data);
    }
}
