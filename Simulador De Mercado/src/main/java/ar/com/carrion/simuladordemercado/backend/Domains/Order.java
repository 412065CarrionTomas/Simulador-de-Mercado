package ar.com.carrion.simuladordemercado.backend.Domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;
    private String typeOrder;
    private int quantity;
    private LocalDateTime time;

    @Override
    public String toString() {
        return String.format("Order{precio=%.2f, tipo='%s', cantidad=%d, tiempo=%s}",
                price,
                typeOrder,
                quantity,
                time);
    }
}
