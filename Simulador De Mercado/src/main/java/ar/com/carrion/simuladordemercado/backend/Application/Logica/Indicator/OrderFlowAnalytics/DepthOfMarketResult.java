package ar.com.carrion.simuladordemercado.backend.Application.Logica.Indicator.OrderFlowAnalytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.usertype.LoggableUserType;

import java.lang.reflect.Array;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepthOfMarketResult {
    private List<Integer> bidSize;
    private List<Double> price;
    private List<Integer> askSize;
}
