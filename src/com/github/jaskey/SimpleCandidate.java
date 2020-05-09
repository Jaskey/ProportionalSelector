package com.github.jaskey;

import java.util.Objects;

/**
 * 简单的候选实体，若上游只关心id，可以使用此类。
 */
public class SimpleCandidate implements ICandidate {
    private final int weight;
    private final String id;

    public SimpleCandidate(String id, int weight) {
        this.weight = weight;
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public String getId() {
        return id;
    }


    @Override
    public String id() {
        return id;
    }

    @Override
    public int weight() {
        return weight;
    }

    @Override
    public String toString() {
        return "SimpleCandidate{" +
                "weight=" + weight +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCandidate that = (SimpleCandidate) o;
        return weight == that.weight &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, id);
    }
}

