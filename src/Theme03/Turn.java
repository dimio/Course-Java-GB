package Theme03;

/**
 * @description Get turns coord for human and computer
 * @version 0.0.2
 * @author Dmitry
 */

import java.util.Scanner;

public class Turn {
    public static void getHumanTurnXY(int[] turnXY) {
        Scanner sc = new Scanner(System.in);

        turnXY[0] = sc.nextInt() - 1; // Считывание номера строки - X
        turnXY[1] = sc.nextInt() - 1; // Считывание номера столбца - Y
    }

    public static void getComputerTurn(int[] turnXY, int[] hTurnXY, int field_size, int mode, int cnt){
        switch (mode) {
            case 1:
                Engine.makeSillyTurn(turnXY, field_size);
                break;
            case 2:
                Engine.makeDummyTurn(turnXY, hTurnXY, field_size, cnt);
                break;
            case 3:
                break;
            default:
                Engine.makeSillyTurn(turnXY, field_size);
                break;
        }
    }
}
