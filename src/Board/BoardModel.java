package Board;

import DTO.TetrisBoard;
import utils.BlockUtils;
import utils.InputHandler;

import java.util.Arrays;

public class BoardModel {
    private BoardView boardView;
    private TetrisBoard tetrisBoard;

    BoardModel(BoardView boardView) {
        this.boardView = boardView;
    }

    public void initBoard(int rowSize, int columnSize) {
        if (tetrisBoard != null)
            return;
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

        char[][] blockToDrop = BlockUtils.getBlock(block);
        System.out.println(Arrays.toString(blockToDrop[0]));
        char[][] board = tetrisBoard.getBoard();

        if (!canPlaceBlockAt(blockToDrop, 0, column)) {
            System.out.println("GAME OVER! Final Score: " + tetrisBoard.getScore());
            displayBoard();
            resetBoard(tetrisBoard.getRowSize(), tetrisBoard.getColumnSize());
            return;
        }

        int row = 0;
        int safeRow = -1;
        char[][] clonedBoard;
        char[][] temp = null;
        String[] blockRef = new String[] { block };

        while (true) {
            clonedBoard = getClonedBoard(board);
            safeRow = dropBlock(row, column, clonedBoard, blockToDrop, block);

            if (safeRow == -1)
                break;

            temp = clonedBoard;
            row++;

            if (!isNextRowSafe(row, column, blockToDrop, tetrisBoard.getBoard())) {
                break;
            }

            System.out.println("Enter instruction : ");
            String instruction = InputHandler.getStringInput();

            if (instruction.isEmpty()) {
                continue;
            }

            if (instruction.equalsIgnoreCase("L")) {
                if (column - 1 >= 0) {
                    column -= 1;
                }
            } else if (instruction.equalsIgnoreCase("R")) {
                if (column + 1 < board[0].length) {
                    column++;
                }
            } else if (blockRef[0].charAt(0) != 'B') {
                blockToDrop = getBlock(instruction, blockRef, blockToDrop, row, column);
            }
        }

        if (temp != null) {
            tetrisBoard.setBoard(temp);

            if (blockRef[0].charAt(0) == 'X') {
                int radius = BlockUtils.getBlockNumber(blockRef[0]);
                int landingRow = safeRow;
                int landingCol = column;
                blastBomb(landingRow, landingCol, radius);
                dropHangingBlocks();
                System.out.println("Bomb blasted! Radius: " + radius);
            } else {
                int clearedRows = clearFullRows();
                if (clearedRows > 0) {
                    tetrisBoard.addScore(clearedRows * 100);
                    System.out.println("Cleared " + clearedRows + " rows ! Score: " + tetrisBoard.getScore());
                }
            }
        }
        displayBoard();
    }

    private char[][] getBlock(String instruction, String[] blockRef, char[][] currentBlock, int row, int column) {
        char[][] result = currentBlock;
        String block = blockRef[0];

        if (instruction.equalsIgnoreCase("C")) {
            int nextVal = getNextVal(block);
            System.out.println("NExt val: " + nextVal);
            String newBlock = String.valueOf(block.charAt(0)) + nextVal;
            char[][] rotatedBlock = BlockUtils.getBlock(newBlock);

            if (canPlaceBlockAt(rotatedBlock, row, column)) {
                result = rotatedBlock;
                blockRef[0] = newBlock;
                System.out.println("Rotated clockwise : " + newBlock);
            } else {
                System.out.println("Cant rotate clockwise");
            }
        } else if (instruction.equalsIgnoreCase("AC")) {
            int val = block.charAt(1) - '0';
            int preVal = (val - 1) == 0 ? 4 : (val - 1);
            System.out.println("preVal: " + preVal);
            String newBlock = String.valueOf(block.charAt(0)) + preVal;
            char[][] rotatedBlock = BlockUtils.getBlock(newBlock);

            if (canPlaceBlockAt(rotatedBlock, row, column)) {
                result = rotatedBlock;
                blockRef[0] = newBlock;
                System.out.println("Rotated Anti-clockwise to: " + newBlock);
            } else {
                System.out.println("Cant rotate Ac");
            }
        }

        return result;
    }

    private boolean canPlaceBlockAt(char[][] block, int row, int column) {
        if (block == null)
            return false;

        char[][] board = tetrisBoard.getBoard();

        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                if (block[i][j] == '0') {
                    int boardRow = row + i;
                    int boardCol = column + j;

                    if (boardRow < 0 || boardRow >= tetrisBoard.getRowSize() ||
                            boardCol < 0 || boardCol >= tetrisBoard.getColumnSize()) {
                        return false;
                    }

                    if (board[boardRow][boardCol] == '0') {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private int getNextVal(String block) {
        int val = block.charAt(1) - '0';
        return (val + 1) == 5 ? 1 : (val + 1);
    }

    private int dropBlock(int row, int column, char[][] clonedBoard, char[][] block, String blockString) {
        int safeRow = row - 1;
        if (!isNextRowSafe(row, column, block, clonedBoard))
            return -1;

        safeRow += 1;
        System.out.println("Safe row : " + safeRow);

        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                if (block[i][j] == 'X' ||
                        (block[i][j] == '0' && isValid(i + safeRow, column + j)))
                    clonedBoard[i + safeRow][column + j] = block[i][j];
            }
        }

        displayBoard(clonedBoard);
        return safeRow;
    }

    private boolean isNextRowSafe(int row, int column, char[][] block, char[][] clonedBoard) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                if (!isValid(i + row, j + column) ||
                        (block[i][j] == '0' && clonedBoard[i + row][column + j] == '0'))
                    return false;
            }
        }

        return true;
    }

    private boolean isNextRowSafe(int row, int col, char[][] block) {
        char[][] tetrisBoard = this.tetrisBoard.getBoard();

        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                if (block[i][j] == '0' && tetrisBoard[i + row][col + j] == '0')
                    return false;
            }
        }

        return true;
    }

    private void displayBoard(char[][] clonedBoard) {
        System.out.println("====== Board ======");

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
        if (tetrisBoard == null) {
            System.out.println("TetrisBoard not initialized");
            return;
        }

        System.out.println("Score           : " + tetrisBoard.getScore());
        System.out.println("======= Board =======");

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
                0 <= row && row < tetrisBoard.getRowSize() &&
                tetrisBoard.getBoard()[row][column] != '0';
    }

    private int clearFullRows() {
        char[][] board = tetrisBoard.getBoard();
        int clearedCount = 0;

        for (int i = board.length - 1; i >= 0; i--) {
            boolean isFull = true;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '-') {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                clearedCount++;
                for (int k = i; k > 0; k--) {
                    board[k] = board[k - 1].clone();
                }
                Arrays.fill(board[0], '-');
                i++;
            }
        }

        return clearedCount;
    }

    private void blastBomb(int row, int col, int radius) {
        char[][] board = tetrisBoard.getBoard();
        int destroyed = 0;

        for (int i = row - radius; i <= row + radius; i++) {
            for (int j = col - radius; j <= col + radius; j++) {
                if (i >= 0 && i < tetrisBoard.getRowSize() &&
                        j >= 0 && j < tetrisBoard.getColumnSize()) {
                    if (board[i][j] != '-') {
                        board[i][j] = '-';
                        destroyed++;
                    }
                }
            }
        }

        System.out.println("Destroyed " + destroyed + " blocks");
    }

    private void dropHangingBlocks() {
        char[][] board = tetrisBoard.getBoard();

        for (int col = 0; col < tetrisBoard.getColumnSize(); col++) {
            for (int row = tetrisBoard.getRowSize() - 2; row >= 0; row--) {
                if (board[row][col] != '-') {
                    int dropRow = row;
                    while (dropRow + 1 < tetrisBoard.getRowSize() && board[dropRow + 1][col] == '-') {
                        dropRow++;
                    }

                    if (dropRow != row) {
                        board[dropRow][col] = board[row][col];
                        board[row][col] = '-';
                    }
                }
            }
        }
    }

    public void resetBoard(int rowSize, int columnSize) {
        tetrisBoard = new TetrisBoard(rowSize, columnSize);

        for (char[] row : tetrisBoard.getBoard()) {
            Arrays.fill(row, '-');
        }
    }
}
