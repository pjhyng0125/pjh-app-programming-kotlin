package com.pjh.kotlinsample;

import java.util.Objects;

public class FruitJava {
    String fruitName;
    String description;

    @Override
    public String toString() {
        return "FruitJava{" +
                "fruitName='" + fruitName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitJava fruitJava = (FruitJava) o;
        return Objects.equals(fruitName, fruitJava.fruitName) && Objects.equals(description, fruitJava.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitName, description);
    }
}
