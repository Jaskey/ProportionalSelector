package com.github.jaskey.sample;


import com.github.jaskey.ICandidate;

import java.util.Objects;

/**
 * Created by linjunjie1103@gmail.com on 2020/5/8.
 * 用来示意可以自定义，实际使用的时候，权重可能是存储于数据库
 */
public class MyCandidate implements ICandidate {
    private String name;


    private int weight;
    private String otherFields;

    public MyCandidate(String name, int weight, String otherFields) {
        this.name = name;
        this.weight = weight;
        this.otherFields = otherFields;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public String id() {
        return name;
    }

    @Override
    public int weight() {
        return weight;
    }

    @Override
    public String toString() {
        return "MyCandidate{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", otherFields='" + otherFields + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCandidate that = (MyCandidate) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
