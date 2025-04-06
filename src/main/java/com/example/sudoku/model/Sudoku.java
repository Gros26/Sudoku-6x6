package com.example.sudoku.model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Sudoku {
    private int size, block_height, block_width;

    ArrayList<ArrayList<Integer>> rows;
    ArrayList<ArrayList<Integer>> columns;

    ArrayList<ArrayList<Integer>> temp_rows;
    ArrayList<ArrayList<Integer>> temp_columns;

    ArrayList<Coordinate> available_cells;


    public Sudoku(int size, int block_height, int block_width, int random_elements) {
        this.size = size;
        this.block_height = block_height;
        this.block_width = block_width;

        initialization();
        fill_randomly(random_elements);
    }

    private void initialization() {
        rows = new ArrayList<ArrayList<Integer>>(size);
        columns = new ArrayList<ArrayList<Integer>>(size);

        temp_rows = new ArrayList<ArrayList<Integer>>(size);
        temp_columns = new ArrayList<ArrayList<Integer>>(size);

        for (int i = 0; i < size; i++) {
            rows.add(new ArrayList<Integer>(size));
            columns.add(new ArrayList<Integer>(size));

            for (int j = 0; j < size; j++) {
                rows.get(i).add(0);
                columns.get(i).add(0);
            }
        }

        available_cells = new ArrayList<Coordinate>(size * size);
    }

    private boolean is_valid_move(int element, int row_position, int column_position) {
        ArrayList<Integer> row = temp_rows.get(row_position);
        ArrayList<Integer> column = temp_columns.get(column_position);
        boolean is_contained_in_row = row.contains(element);
        boolean is_contained_in_column = column.contains(element);

        if (!is_contained_in_row && !is_contained_in_column) {
            // Check chunk
            HashSet<Integer> chunk_elements = new HashSet<Integer>(size);
            int block_start_row = row_position / block_height * block_height;
            int block_start_column = column_position / block_width * block_width;
            for (int i = block_start_row; i < block_start_row + block_height; i++) {
                for (int j = block_start_column; j < block_start_column + block_width; j++) {
                    int cell_value = temp_rows.get(i).get(j);
                    if (cell_value == 0)
                        continue;
                    else if (!chunk_elements.add(cell_value))
                        return false;
                }
            }
            return true;
        } else
            return false;
    }

    public boolean solve() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                int element = temp_rows.get(i).get(j);
                if (element == 0) { // Empty cell
                    for (int k = 1; k <= size; k++) {
                        if (is_valid_move(k, i, j)) {
                            temp_rows.get(i).set(j, k);
                            temp_columns.get(j).set(i, k);

                            if (solve())
                                return true;

                            temp_rows.get(i).set(j, 0); // Backtrack
                            temp_columns.get(j).set(i, 0);
                        }
                    }
                    return false; // No valid number found
                }
            }

        return true;
    }

    public void copy_original_board_into_temp() {
        temp_rows.clear();
        temp_columns.clear();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row_copy = new ArrayList<Integer>(rows.get(i));
            ArrayList<Integer> column_copy = new ArrayList<Integer>(columns.get(i));

            temp_rows.add(row_copy);
            temp_columns.add(column_copy);
        }
    }

    private void fill_randomly(int n) {
        if (n > size * size)
            throw new IllegalArgumentException("Error: number of random elements exceeds the " +
                    "total number of cells in the Sudoku grid");

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                available_cells.add(new Coordinate(i, j));

        for (int i = 0; i < n; i++) {
            int value;

            int random_index = (int) (Math.random() * available_cells.size());
            Coordinate coordinate = available_cells.remove(random_index);
            int row = coordinate.row;
            int column = coordinate.column;

            boolean invalid_value = true;
            copy_original_board_into_temp();

            do {
                value = (int) (Math.random() * size) + 1;

                temp_rows.get(row).set(column, value);
                temp_columns.get(column).set(row, value);

                if (solve())
                    invalid_value = false;
            } while (invalid_value);

            rows.get(row).set(column, value);
            columns.get(column).set(row, value);
        }
    }

    public void print_board(String option) {
        ArrayList<ArrayList<Integer>> board;

        if (option == "original")
            board = rows;
        else
            board = temp_rows;

        System.out.println("+" + "-".repeat(size * 2 + 3) + "+"); // 1 + size * 2 + 3 + 1 = 2 * size + 5
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0 && i > 0)
                System.out.println("+" + "-".repeat(size * 2 + 3) + "+");

            System.out.print("| ");
            for (int j = 0; j < size; j++) {
                int value = board.get(i).get(j);
                System.out.print(value == 0 ? "." : value);
                if (j % 3 == 2)
                    System.out.print(" | ");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("+" + "-".repeat(size * 2 + 3) + "+");
    }


    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Current Board:");
            print_board("original");
            System.out.println("Enter your move in the format 'row column value' (or type \"solve\" to solve the puzzle, \"exit\" to quit):");
            String input = scanner.nextLine().trim();

            copy_original_board_into_temp();

            if (input.equalsIgnoreCase("solve")) {
                if (solve()) {
                    System.out.println("Solved Puzzle:");
                    print_board("temp");
                } else
                    System.out.println("No solution exists for the current board");
                break;
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the game. Goodbye!");
                break;
            } else {
                try {
                    String[] parts = input.split("\\s+");
                    if (parts.length != 3) {
                        System.out.println("Invalid input format. Please use \"row column value\"");
                        continue;
                    }

                    int row = Integer.parseInt(parts[0]) - 1;
                    int column = Integer.parseInt(parts[1]) - 1;
                    int value = Integer.parseInt(parts[2]);

                    Coordinate coordinate = new Coordinate(row, column);

                    if (!available_cells.contains(coordinate))
                        System.out.println("This cell is either full or invalid. Please choose another cell");

                    if (is_valid_move(value, row, column)) {
                        temp_rows.get(row).set(column, value);
                        temp_columns.get(column).set(row, value);
                        if (solve()) {
                            rows.get(row).set(column, value);
                            columns.get(column).set(row, value);
                            System.out.println("Move accepted");
                        } else
                            System.out.println("Valid move, but the puzzle cannot be solved from this state. Reverting move...");
                    } else
                        System.out.println("Invalid move. The value conflicts with Sudoku rules");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numbers for row, column, and value");
                }
            }
        }
        scanner.close();
    }

    public ArrayList<ArrayList<Integer>> getTemp_rows() {
        return temp_rows;
    }

    public ArrayList<ArrayList<Integer>> getRows() {
        return rows;
    }
}
