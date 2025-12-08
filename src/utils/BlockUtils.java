package utils;
import DTO.*;

public class BlockUtils {
    public static char[][] getBlock(String block) {
        char blockName = block.charAt(0);
        char[][] resultBlock = null;
        switch (blockName) {
            case 'L':
                resultBlock = lBlock(block);
                break;
            case 'B':
                resultBlock = bBlock(block);
                break;
            case 'Z':
                resultBlock = zBlock(block);
                break;
            case 'I':
                resultBlock = iBlock(block);
                break;
            case 'S':
                resultBlock = sBlock(block);
                break;
            case 'T':
                resultBlock = tBlock(block);
                break;
            default:
                System.out.println("Invalid Block");
                break;
        }

        return resultBlock;
    }

    private static char[][] sBlock(String block) {
        return SBlock.getBlock(getBlockNumber(block));
    }

    private static char[][] tBlock(String block) {
        return TBlock.getBlock(getBlockNumber(block));
    }
    private static char[][] iBlock(String block) {
        return IBlock.getBlock(getBlockNumber(block));
    }

    private static char[][] zBlock(String block) {
        return ZBlock.getBlock(getBlockNumber(block));
    }

    private static char[][] bBlock(String block) {
        return BBlock.getBlock(getBlockNumber(block));
    }

    private static char[][] lBlock(String block) {
        return LBlock.getBlock(getBlockNumber(block));
    }

    public static int getBlockNumber(String block) {
        return block.charAt(1) - '0';
    }
}
