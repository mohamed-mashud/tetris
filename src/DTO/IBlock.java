package DTO;

public class IBlock {
    public static char[][] getBlock(int blockNumber) {
        char[][] result = null;

        switch (blockNumber) {
            case 1:
            case 3:
                result = new char[][] {
                        new char[]{'0'},
                        new char[]{'0'},
                        new char[]{'0'},
                        new char[]{'0'}
                };
                break;
            case 2:
            case 4:
                result = new char[][] {
                        new char[]{'0', '0', '0', '0'}
                };
                break;
            default:
                System.out.println("Invald");
                break;
        }

        return result;
    }
}
