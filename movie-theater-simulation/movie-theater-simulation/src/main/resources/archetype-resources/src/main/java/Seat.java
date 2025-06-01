package org.example;

public class Seat {
    private final int row;
    private final int col;
    private boolean reserved;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
        this.reserved = false;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void reserve() {
        this.reserved = true;
    }

    public void cancel() {
        this.reserved = false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return reserved ? "[X]" : "[ ]";
    }
}