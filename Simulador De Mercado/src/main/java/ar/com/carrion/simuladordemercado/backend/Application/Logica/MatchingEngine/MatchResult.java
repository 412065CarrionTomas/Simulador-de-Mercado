package ar.com.carrion.simuladordemercado.backend.Application.Logica.MatchingEngine;

import ar.com.carrion.simuladordemercado.backend.Domains.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResult {
    private Order mainOrder;
    /*aqui almacenaria lo mismo que el ultimo elemento de la lista mainOrdersModified
     * */
    private List<OrderResult> mainOrdersModified;
    private List<OrderResult> oppositeOrdersModified;
    private double lastTradeExecute;
}
