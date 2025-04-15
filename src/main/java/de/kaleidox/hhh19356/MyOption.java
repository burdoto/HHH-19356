package de.kaleidox.hhh19356;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MyOption {
    public static final Map<String, MyOption> CACHE    = new HashMap<>();
    public static final MyOption              OPTION_A = new MyOption("A");
    public static final MyOption              OPTION_B = new MyOption("B");

    public static MyOption of(String option) {
        return CACHE.get(option);
    }

    private final String name;

    private MyOption(String name) {
        this.name = name;

        CACHE.put(name, this);
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyOption myOption = (MyOption) o;
        return Objects.equals(getName(), myOption.getName());
    }
}
