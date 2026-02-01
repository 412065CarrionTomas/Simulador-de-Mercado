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
public class Candle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long time;
    private String timeFrame;
    private double open;
    private double close;
    private double low;
    private double high;

    @Override
    public String toString() {
        return "Candle{" +
                "id=" + id +
                ", timeClose=" + time +
                ",timeFrame="+ timeFrame+
                ", openPrice=" + open +
                ", closePrice=" + close +
                ", lowExtremePrice=" + low +
                ", highExtremePrice=" + high +
                '}';
    }
}