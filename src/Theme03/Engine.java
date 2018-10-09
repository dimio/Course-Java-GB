package Theme03;

/**
 * @description Simple tic-tac-toe engine
 * @version 0.0.2
 * @author Dmitry
 */

import java.util.Random;

public class Engine {
    private static Random random = new Random();

    /**
     *
     * @param i - "y" coord for current cell
     * @param j - "x" coord for current cell
     * @param size - game field size (map.length)
     * @return int[] length, where:
     * length[0], length[1] - left-to-right and
     * right-to-left diagonals lengths
     */
    public static int[] getDiagonalsLength(int i, int j, int size){
        int length[] = new int[2];
        // для диагонали "слева-направо":
        int min1 = Math.min(i, j);
        int max1 = Math.max(i, j);
        // для диагонали "справа-налево":
        // (длина диагонали будет равна длине диагонали LtR,
        // построенной через противолежащую относительно оси y точку)
        int j2 = size - j - 1;
        int min2 = Math.min(i, j2);
        int max2 = Math.max(i, j2);

        // diagonal LtR
        length[0] = (size - max1) + min1;
        // diagonal RtL
        length[1] = (size - max2) + min2;

        return length;
    }

    public static void makeSillyTurn(int[] turnXY, int field_size) {
        turnXY[0] = random.nextInt(field_size);
        turnXY[1] = random.nextInt(field_size);
    }

    public static void makeDummyTurn(int[] turnXY, int[] hTurnXY, int field_size, int cnt){
        // попробовать выбрать соседнюю свободную ячейку
        if (cnt < 30){
            turnXY[0] = hTurnXY[0] + random.nextInt(2)-1;
            turnXY[1] = hTurnXY[1] + random.nextInt(2)-1;
        }
        else {
            makeSillyTurn(turnXY, field_size);
        }
    }
}
