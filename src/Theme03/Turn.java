package Theme03;

/**
 * @description Get turns coord for human and computer
 * @version 0.0.3
 * @author Dmitry (dimio-blog@gmail.com)
 */

import java.util.Scanner;

public class Turn {
    public static void getHumanTurnXY(int[] turnXY) {
        Scanner sc = new Scanner(System.in);

        turnXY[0] = sc.nextInt() - 1; // Считывание номера строки - X
        turnXY[1] = sc.nextInt() - 1; // Считывание номера столбца - Y
    }

    public static void getComputerTurn(int[] turnXY, int[] hTurnXY, int winingStreakSize, int mode, int cnt, char[] CELLS, char[][] map){
        switch (mode) {
            case 1:
                Engine.makeSillyTurn(turnXY, map.length);
                break;
            case 2:
                Engine.makeDummyTurn(turnXY, hTurnXY, map.length, cnt);
                break;
            case 3:
                Engine.makeStupidTurn(turnXY, winingStreakSize, CELLS, map);
                break;
            default:
                Engine.makeSillyTurn(turnXY, map.length);
                break;
        }
    }
}
