package ie.tudublin;

import java.util.Map;

import processing.core.PApplet;

public class LifeBoard {

    boolean[][] board;
    boolean[][] next;

    int rows;
    int cols;

    float cellWidth;
    float cellHeight;
    PApplet p;

    public LifeBoard(int rows, int cols, PApplet p) {
        this.rows = rows;
        this.cols = cols;
        this.p = p;
        board = new boolean[rows][cols];
        cellWidth = p.width / (float) cols;
        cellHeight = p.height / (float) rows;

    }

    void randomize() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float dice = p.random(1.0f);
                board[row][col] = (dice < 0.5f);
            }
        }
    }

    public void update() {
        boolean[][] updatedBoard = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int aliveNeighbors = countCells(row, col);

                if (board[row][col]) {
                    if (aliveNeighbors < 2 || aliveNeighbors > 3) {
                        updatedBoard[row][col] = false;
                    } else {
                        updatedBoard[row][col] = true;
                    }
                } else {
                    if (aliveNeighbors == 3) {
                        updatedBoard[row][col] = true;
                    }
                }

            }
        }

        boolean[][] temp = board;
        board = updatedBoard;
        updatedBoard = temp;
        // The rules are!
        // If the cell is alive then
        // If it has < 2 alive neighbours, it dies due to lonelyness
        // If it has > 3 alive neighbours, it dies due to overcrouding
        // If it has 2-3 live neighbours, it survives
        // If the cell is dead, then it comes to life
        // in the next generation if it has exactly 3
        // live neighbours

        // Write a nested for loop to update the board each frame
        // Read board, write to next

        // At the end, swap them like this:
        // boolean[][] temp = board;
        // board = next;
        // next = temp;
    }

    public void setCell(int row, int col, boolean value) {
        if (row > 0 && col > 0 && row < rows && col < cols) {
            board[row][col] = value;
        }
    }

    public boolean getCell(int row, int col) {
        if (row > 0 && col > 0 && row < rows && col < cols) {
            return board[row][col];
        }
        return false;
    }

    public int countCells(int row, int col) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) {
                    continue;
                }

                if (i >= 0 && i < rows && j >= 0 && j < cols) {
                    if (board[i][j]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public void render() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float x = p.map(col, 0, cols, 0, p.width);
                float y = row * cellHeight;
                p.stroke(200, 255, 255);
                if (board[row][col]) {
                    p.fill(100, 255, 255);
                } else {
                    p.noFill();
                }
                p.rect(x, y, cellWidth, cellHeight);
            }
        }

    }

}
