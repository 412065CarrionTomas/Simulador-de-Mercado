package ar.com.carrion.simuladordemercado.backend.Domains;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Candle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String timeFrame;
    private LocalDateTime time;
    private double open;
    private double close;
    private double low;
    private double high;

    @Override
    public String toString() {
        return "Candle{" +
                "id=" + id +
                ", timeClose=" + time +
                ", openPrice=" + open +
                ", closePrice=" + close +
                ", lowExtremePrice=" + low +
                ", highExtremePrice=" + high +
                '}';
    }
}


//@Override
//public String toString() {
//    return "Candle{" +
//            "id=" + id +
//            ", timeFrame='" + timeFrame + '\'' +
//            ", timeClose=" + timeClose +
//            ", openPrice=" + openPrice +
//            ", closePrice=" + closePrice +
//            ", lowExtremePrice=" + lowExtremePrice +
//            ", highExtremePrice=" + highExtremePrice +
//            '}';
//}