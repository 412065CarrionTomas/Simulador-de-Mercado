package ar.com.carrion.simuladordemercado.backend.Domains;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double openPrice;
    private double closePrice;
    private double lowExtremePrice;
    private double highExtremePrice;
}

