package ar.com.carrion.simuladordemercado.backend.Domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

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

    private Long idOrder;
    private LocalDateTime creationOrderTime; //Tiempo en el que se creo la orden(viene de componente RandomAlgorithm)

    // Evento
    private String eventType;          // CREATE, PENDING, FILLED, COMPLETE_FILLED
    private LocalDateTime eventTime; //Dude en si asignarle el horario apenas ingresaba a la cola, o cuando hacia el registro.

    // Estado de la orden en ese momento
    private double price;
    private String orderType;
    private int quantity;
    private int filledQuantity;      // Cuánto se ejecutó hasta ahora
    private int remainingQuantity;     // Cuánto falta

    // Ejecución (si aplicable)
    private double executionPrice; // Precio real de ejecución
    private Boolean fullyExecuted;     // Flag rápido
    private Long idOrderOpposite;
}

/*
 * Lo ideal seria una PK compuesta entre tiempo de creacion de orden + 1,2,3,4,...,n id. Pero que ese
 * id siempre comience de nuevo en 1 por cada tiempo de creacion distinto. Sin emabrgo, no se como manejarlo
 * y no es mi objetivo por ahora. Tener en cuenta para futuro
 * */
//    private Long sequenceId;