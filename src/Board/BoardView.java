package Board;

import utils.InputHandler;

public class BoardView {
    private BoardModel boardModel;

    public BoardView() {
        this.boardModel = new BoardModel(this);
    }

    public void start() {
        while (true) {
            System.out.println("""
                    ==== WELCOME TO TETRIS ===
                    1) Initialize Board
                    2) Drop a Block
                    3) Display Board
                    4) Reset board
                    5) Exit
                    """);
            int choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1:
                    initBoard();
                    break;
                case 2:
                    dropBlock();
                    break;
                case 3:
                    displayBoard();
                    break;
                case 4:
                    resetBoard();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input!!  try again.......");
            }
        }
    }

    private void resetBoard() {
//        System.out.println("Enter the number of Rows : ");
//        int rowSize = InputHandler.getIntegerInput();
//        System.out.println("Enter the number of Columns : ");
//        int columnSize = InputHandler.getIntegerInput();
        boardModel.resetBoard(15, 10);
    }

    private void displayBoard() {
        boardModel.displayBoard();
    }

    private void dropBlock() {
        System.out.println("Enter column to drop : ");
        int column = InputHandler.getIntegerInput();

        System.out.println("Enter block to drop : ");
        String block = InputHandler.getStringInput();

        boardModel.dropBlock(column, block);
    }

    private void initBoard() {
//        System.out.println("Enter the number of Rows : ");
//        int rowSize = InputHandler.getIntegerInput();
//        System.out.println("Enter the number of Columns : ");
//        int columnSize = InputHandler.getIntegerInput();

        boardModel.initBoard(15,10);
    }
}
