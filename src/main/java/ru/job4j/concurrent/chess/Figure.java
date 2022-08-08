package ru.job4j.concurrent.chess;

public interface Figure {
    Cell position();
    Cell[] way(Cell source, Cell dest);
    Figure copy(Cell dest);
}
