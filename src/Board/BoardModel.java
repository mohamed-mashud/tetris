package Board;

import DTO.TetrisBoard;
import utils.BlockUtils;

import java.util.Arrays;

public class BoardModel {
    private BoardView boardView;
    private TetrisBoard tetrisBoard;
    BoardModel(BoardView boardView) {
        this.boardView = boardView;
    }

    public void initBoard(int rowSize, int columnSize) {
        if(tetrisBoard != null)     return;
        tetrisBoard = new TetrisBoard(rowSize, columnSize);

        for (char[] row : tetrisBoard.getBoard()) {
            Arrays.fill(row, '-');
        }
    }

    public void dropBlock(int column, String block) {
        if (tetrisBoard == null) {
            System.out.println("initialize board");
            return;
        }

        if (!isValid(0, column))   {
            System.out.println("Cant place block in this column");
            return;
        }

        char[][] blockToDrop = BlockUtils.getBlock(block);
        int row = 0;
        boolean currentlyPossible;

//        do {
            currentlyPossible = dropBlock(row, column, blockToDrop, block);
            System.out.println("Enter instruction ");
//        } while(currentlyPossible);

    }

    private boolean dropBlock(int row, int column, char[][] block, String blockString) {
        int safeRow = -1;
        for (; row <= tetrisBoard.getRowSize() - block.length; row++) {
            if (!isNextRowSafe(row, column, block))    break;
            safeRow = row;
        }

        if (safeRow == -1) {
            return false;
//            System.out.println("Game Over");
//            System.exit(0);
        }

        System.out.println("Safe row : " + safeRow);
        char[][] board = tetrisBoard.getBoard();
//        fillRowAndCol(safeRow, column, blockToDrop);

        char[][] clonedBoard = getClonedBoard(board);


        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                if(block[i][j] == '0')
                    board[i + safeRow][column + j] = block[i][j];
            }
        }

        displayBoard();
        return true;
    }

    private void displayBoard(char[][] clonedBoard) {
        System.out.println("========= Board =========");

        for (char[] row : clonedBoard) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    public char[][] getClonedBoard(char[][] board) {
        char[][] result = new char[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            result[i] = board[i].clone();
        }

        return result;
    }


    public void displayBoard() {
        if (tetrisBoard == null)  {
            System.out.println("TetrisBoard not initialized");
            return;
        }
        System.out.println("========= Board =========");

        char[][] tetrisBoard = this.tetrisBoard.getBoard();

        for (char[] row : tetrisBoard) {
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }

    private boolean isValid(int row, int column) {
        return 0 <= column && column < tetrisBoard.getColumnSize() &&
                0 <= row && row < tetrisBoard.getRowSize();
    }

    private boolean isNextRowSafe(int row, int col, char[][] block) {
        char[][] tetrisBoard = this.tetrisBoard.getBoard();

        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                if (block[i][j] == '0' && tetrisBoard[i + row][col + j] == '0') return false;
            }
        }

        return true;
    }

    public void resetBoard(int rowSize, int columnSize) {
        tetrisBoard = new TetrisBoard(rowSize, columnSize);

        for (char[] row : tetrisBoard.getBoard()) {
            Arrays.fill(row, '-');
        }
    }
}
