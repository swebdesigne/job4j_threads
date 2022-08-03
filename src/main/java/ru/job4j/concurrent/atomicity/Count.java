package ru.job4j.concurrent.atomicity;

public class Count {
    private int value;

    public void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }
}
