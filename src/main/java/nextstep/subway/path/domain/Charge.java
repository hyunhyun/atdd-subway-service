package nextstep.subway.path.domain;

import java.util.Objects;

public class Charge {
    public static final Charge ZERO = new Charge(0);
    private final int value;

    public Charge(int value) {
        this.value = value;
    }

    public Charge add(Charge charge) {
        return new Charge(this.value + charge.value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Charge)) return false;
        Charge charge = (Charge) o;
        return value == charge.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
