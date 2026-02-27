package ar.com.carrion.simuladordemercado.backend.Domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "order_snapshots")
@Component
public class OrderSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sequenceId;
    private LocalDateTime creationOrderTime; //Tiempo en el que se creo la orden(viene de componente RandomAlgorithm)

    // Evento
    private String eventType;          // CREATE, PENDING, FILLED, COMPLETE_FILLED
    private LocalDateTime eventTime; //Dude en si asignarle el horario apenas ingresaba a la cola, o cuando hacia el registro.

    // Estado de la orden en ese momento
    private double price;
    private String orderType;
    private int originalQuantity;
    private int executedQuantity;      // Cuánto se ejecutó hasta ahora
    private int remainingQuantity;     // Cuánto falta

    // Ejecución (si aplicable)
    private double executionPrice; // Precio real de ejecución
    private Boolean fullyExecuted;     // Flag rápido



}
