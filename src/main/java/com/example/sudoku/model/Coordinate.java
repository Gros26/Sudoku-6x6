package com.example.sudoku.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Coordinate class represents a pair of row and column indices for a cell in a Sudoku grid.
 * It is used to keep track of the position of a cell in the grid.
 * @author Juan David Lopez
 * @author Grosman Klein Garcia Valencia
 * @version 1.0
 */
public class Coordinate implements Serializable {

    /** The row index of the cell */
    public final Integer row;

    /** The column index of the cell */
    public final Integer column;

    /**
     * Constructs a new Coordinate object with the specified row and column.
     *
     * @param row The row index of the cell in the Sudoku grid.
     * @param column The column index of the cell in the Sudoku grid.
     */
    public Coordinate(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Compares this Coordinate object to another object for equality.
     * Two Coordinate objects are considered equal if their row and column values are the same.
     *
     * @param obj The object to compare this Coordinate to.
     * @return true if the specified object is equal to this Coordinate, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return Objects.equals(row, that.row) && Objects.equals(column, that.column);
    }

    /**
     * Returns a hash code value for this Coordinate.
     * The hash code is generated based on the row and column values.
     *
     * @return a hash code value for this Coordinate.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Returns a string representation of the Coordinate in the form "(row, column)".
     *
     * @return a string representation of the Coordinate.
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
