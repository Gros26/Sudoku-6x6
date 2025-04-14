package com.example.sudoku.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a Sudoku game model. This class encapsulates the logic for managing a Sudoku grid,
 * solving the puzzle, validating moves, and generating a game board with random elements or fixed distributions.
 *
 * <p>This class implements the Sudoku logic with methods to initialize the grid, check the validity of moves,
 * and provide solutions to the puzzle.</p>
 *
 * <p>The class also handles generating random Sudoku boards and checking if the user's moves are correct.</p>
 */
public class Sudoku implements Serializable {
    private int size, block_height, block_width;

    ArrayList<ArrayList<Integer>> rows;
    ArrayList<ArrayList<Integer>> columns;

    ArrayList<ArrayList<Integer>> temp_rows;
    ArrayList<ArrayList<Integer>> temp_columns;

    ArrayList<Coordinate> available_cells;

    /**
     * Initializes a Sudoku board with a given size, block height, block width, and a flag to determine
     * whether to use a fixed distribution or generate random elements.
     *
     * @param size The size of the Sudoku board (e.g., 6 for a 6x6 grid).
     * @param block_height The height of the blocks in the Sudoku grid.
     * @param block_width The width of the blocks in the Sudoku grid.
     * @param useFixedDistribution Flag indicating whether to use a fixed distribution of elements or generate them randomly.
     */
    public Sudoku(int size, int block_height, int block_width, boolean useFixedDistribution) {
        this.size = size;
        this.block_height = block_height;
        this.block_width = block_width;

        initialization();

        if (useFixedDistribution) {
            fillWithTwoCellsPerBlock();
        } else {
            // The default number of random elements, for example 12 for a 6x6 grid.
            int defaultRandomElements = 12;
            fill_randomly(defaultRandomElements);
        }
    }

    /**
     * Initializes the Sudoku grid, setting up the rows, columns, temporary grids, and available cells.
     */
    private void initialization() {
        rows = new ArrayList<>(size);
        columns = new ArrayList<>(size);
        temp_rows = new ArrayList<>(size);
        temp_columns = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            rows.add(new ArrayList<>(size));
            columns.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                rows.get(i).add(0);
                columns.get(i).add(0);
            }
        }

        available_cells = new ArrayList<>(size * size);
    }

    /**
     * Validates whether a given move is valid according to Sudoku rules. The move must not repeat within
     * the same row, column, or block.
     *
     * @param element The value to be placed in the grid.
     * @param row_position The row position in the grid.
     * @param column_position The column position in the grid.
     * @return true if the move is valid; false otherwise.
     */
    private boolean is_valid_move(int element, int row_position, int column_position) {
        ArrayList<Integer> row = temp_rows.get(row_position);
        ArrayList<Integer> column = temp_columns.get(column_position);
        boolean is_contained_in_row = row.contains(element);
        boolean is_contained_in_column = column.contains(element);

        if (!is_contained_in_row && !is_contained_in_column) {
            // Check chunk (block)
            HashSet<Integer> chunk_elements = new HashSet<>(size);
            int block_start_row = row_position / block_height * block_height;
            int block_start_column = column_position / block_width * block_width;
            for (int i = block_start_row; i < block_start_row + block_height; i++) {
                for (int j = block_start_column; j < block_start_column + block_width; j++) {
                    int cell_value = temp_rows.get(i).get(j);
                    if (cell_value == 0) continue;
                    else if (!chunk_elements.add(cell_value)) return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fills the Sudoku board with two cells per block and generates a valid puzzle.
     */
    private void fillWithTwoCellsPerBlock() {
        // Clears the board first
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rows.get(i).set(j, 0);
                columns.get(j).set(i, 0);
            }
        }

        // Generate a complete solution first
        copy_original_board_into_temp();
        solve();

        // Copy the solution to the main board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rows.get(i).set(j, temp_rows.get(i).get(j));
                columns.get(j).set(i, temp_rows.get(i).get(j));
            }
        }

        // Now, remove cells, keeping only two per block
        List<Coordinate> cellsToKeep = new ArrayList<>();

        // For each block, select two cells to keep
        for (int blockRow = 0; blockRow < size / block_height; blockRow++) {
            for (int blockCol = 0; blockCol < size / block_width; blockCol++) {
                List<Coordinate> blockCells = new ArrayList<>();
                int startRow = blockRow * block_height;
                int startCol = blockCol * block_width;

                // Collect all the cells from this block
                for (int r = startRow; r < startRow + block_height; r++) {
                    for (int c = startCol; c < startCol + block_width; c++) {
                        blockCells.add(new Coordinate(r, c));
                    }
                }

                // Select two cells randomly to keep
                for (int i = 0; i < 2; i++) {
                    if (!blockCells.isEmpty()) {
                        int index = (int) (Math.random() * blockCells.size());
                        cellsToKeep.add(blockCells.remove(index));
                    }
                }
            }
        }

        // Now, remove all cells except the ones we want to keep
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordinate current = new Coordinate(i, j);
                boolean keep = false;

                for (Coordinate coord : cellsToKeep) {
                    if (coord.row == i && coord.column == j) {
                        keep = true;
                        break;
                    }
                }

                if (!keep) {
                    rows.get(i).set(j, 0);
                    columns.get(j).set(i, 0);
                }
            }
        }

        // Update the available cells list
        available_cells.clear();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (rows.get(i).get(j) == 0) {
                    available_cells.add(new Coordinate(i, j));
                }
            }
        }
    }

    /**
     * Solves the Sudoku puzzle using a backtracking algorithm.
     *
     * @return true if the puzzle is solved; false if no solution exists.
     */
    public boolean solve() {
        for (int i = 0; i < size; i++) {
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
        }

        return true;
    }

    /**
     * Copies the original board into the temporary board to allow solving.
     */
    public void copy_original_board_into_temp() {
        temp_rows.clear();
        temp_columns.clear();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row_copy = new ArrayList<>(rows.get(i));
            ArrayList<Integer> column_copy = new ArrayList<>(columns.get(i));

            temp_rows.add(row_copy);
            temp_columns.add(column_copy);
        }
    }

    /**
     * Fills the board with randomly placed numbers.
     *
     * @param n The number of random elements to place on the board.
     */
    private void fill_randomly(int n) {
        if (n > size * size)
            throw new IllegalArgumentException("Error: number of random elements exceeds the " +
                    "total number of cells in the Sudoku grid");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                available_cells.add(new Coordinate(i, j));
            }
        }

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

                if (solve()) {
                    invalid_value = false;
                }
            } while (invalid_value);

            rows.get(row).set(column, value);
            columns.get(column).set(row, value);
        }
    }

    /**
     * Prints the board for debugging purposes.
     *
     * @param option The option to print either the original or the temporary board.
     */
    public void print_board(String option) {
        ArrayList<ArrayList<Integer>> board;

        if (option.equals("original")) {
            board = rows;
        } else {
            board = temp_rows;
        }

        System.out.println("+" + "-".repeat(size * 2 + 3) + "+");
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

    /**
     * Starts the game loop where the player makes moves to fill the Sudoku grid.
     */
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
                } else {
                    System.out.println("No solution exists for the current board");
                }
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
                        } else {
                            System.out.println("Valid move, but the puzzle cannot be solved from this state. Reverting move...");
                        }
                    } else {
                        System.out.println("Invalid move. The value conflicts with Sudoku rules");
                    }
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

