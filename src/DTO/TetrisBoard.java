package DTO;

public class TetrisBoard {
    private final char[][] board;
    public final int rowSize;
    public final int columnSize;

    public TetrisBoard(int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        board = new char[rowSize][columnSize];
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
}
