package DTO;

public class TetrisBoard {
    private char[][] board;
    public final int rowSize;
    public final int columnSize;
    private int score;

    public TetrisBoard(int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        board = new char[rowSize][columnSize];
        this.score = 0;
    }

    public void setBoard(char[][] board) {
        char[][] result = new char[getRowSize()][getColumnSize()];
        for (int i = 0; i < getRowSize(); i++) {
            result[i] = board[i].clone();
        }

        this.board = result.clone();
    }

    public char[][] getBoard() {
        return board;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }
}
