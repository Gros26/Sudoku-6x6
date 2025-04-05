package com.example.sudoku.model;

import java.util.Objects;

public class Coordinate {
    public final Integer row;
    public final Integer column;


    public Coordinate(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return Objects.equals(row, that.row) && Objects.equals(column, that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
