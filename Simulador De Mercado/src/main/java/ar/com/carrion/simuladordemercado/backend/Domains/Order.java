package ar.com.carrion.simuladordemercado.backend.Domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String typeOrder;
    private Price price;
    private int quantity;
    private LocalDateTime time;
}
