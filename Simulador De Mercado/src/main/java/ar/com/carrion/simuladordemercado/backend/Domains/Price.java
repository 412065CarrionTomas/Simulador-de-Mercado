package ar.com.carrion.simuladordemercado.backend.Domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private double value;

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                '}';
    }

}

