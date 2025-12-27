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
    private double price;

    @Override
    public int compareTo(Price other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return Double.compare(price.price, getPrice()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(price);
    }
}

