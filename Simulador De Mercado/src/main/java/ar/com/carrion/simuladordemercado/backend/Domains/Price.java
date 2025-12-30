package ar.com.carrion.simuladordemercado.backend.Domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Comparable<Price> {
    private double value;

    @Override
    public int compareTo(Price other) {
        return Double.compare(this.value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return Double.compare(price.value, getValue()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}

