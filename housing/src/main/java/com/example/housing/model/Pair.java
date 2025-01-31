package com.example.housing.model;

public class Pair<M, B> {
    private Member key;
    private Book value;

    public Pair(Member key, Book value) {
        this.key = key;
        this.value = value;
    }

    public Member getKey() {
        return key;
    }

    public Book getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

}
