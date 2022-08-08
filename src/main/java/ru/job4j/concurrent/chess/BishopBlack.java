package ru.job4j.concurrent.chess;

public class BishopBlack implements Figure {
    private final Cell position;

    public BishopBlack(Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        throw new IllegalStateException(
                String.format("Could not way by diagonal from %s to %s", source, dest)
        );
    }

    @Override
    public Figure copy(Cell dest) {
        return new BishopBlack(dest);
    }
}
