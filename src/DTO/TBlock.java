package DTO;

public class TBlock {
    public static char[][] getBlock(int blockNumber) {
        char[][] result = null;
        switch (blockNumber) {
            case 1:
                result = new char[][]{
                        new char[]{'0', '0', '0'},
                        new char[]{'-', '0', '-'}
                };
                break;
            case 2:
                result = new char[][]{
                        new char[]{'-', '0'},
                        new char[]{'0', '0'},
                        new char[]{'-', '0'},
                };
                break;
            case 3:
                result = new char[][]{
                        new char[]{'-', '0', '-'},
                        new char[]{'0', '0', '0'}
                };
                break;
            case 4:
                result = new char[][]{
                        new char[]{'0', '-'},
                        new char[]{'0', '0'},
                        new char[]{'0', '-'},
                };
                break;
            default:
                System.out.println("Invalid number");
                break;
        }
        return result;
    }
}
