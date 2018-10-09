package Theme03;

/**
 * @description Simple TicTacToe training game on Java in procedural style
 * @version 0.0.2
 * @author Dmitry
 */

import java.util.Scanner;

public class TacTacToe {
    public static void main(String[] args) {
        /** 1 - простая
         * 2 - средняя
         * 3 - сложная
         */
        //TODO: запращивать сложность при старте игры
        int difficulty = 2;

        int field_size;
        int[] hTurnXY = new int[2];
        int[] cTurnXY = new int[2];
        char[] CELLS = {'O', 'X', '_'};
        char winner = CELLS[2]; //в начале игры победителя нет
        Scanner sc = new Scanner(System.in);

        System.out.println("Игра \"Крестики-нолики\". Ваш ход - крестик, вы ходите первым.");

        do {
            System.out.print("Введите размер стороны квадратного поля (целое число >= 2): ");
            field_size = sc.nextInt();
        }
        while (!Map.isSizeCorrect(field_size));

        // передача значения по умолчанию делается через перегрузку метода,
        // пока нет смысла усложнять программу.
        int winingStreakSize; // или по умолчанию делать равным field_size
        do {
            System.out.print("Введите размер победной серии (От 2 до размера стороны поля): ");
            winingStreakSize = sc.nextInt();
        }
        while(winingStreakSize < 2 || winingStreakSize > field_size);

        float turn = 0;
        int max_turns = field_size*field_size;
        char[][] map = Map.initMap(field_size, CELLS[2]);

        while (true){
            Map.printMap(map);

            // ходит Игрок1 (человек)
            do {
                System.out.print("Введите целыми числами через пробел координаты ячейки хода (строка столбец): ");
                Turn.getHumanTurnXY(hTurnXY);
            }
            while (!Map.isCellValid(hTurnXY, map));
            Map.updateMap(hTurnXY, map, CELLS[1]);
            turn++;
            if ( (int)Math.ceil(turn/2) >= winingStreakSize &&
                    Game.isWin(winingStreakSize, hTurnXY, map) )
            {
                winner = CELLS[1];
                break;
            }
            else if ( (int)turn >= max_turns ){
                break;
            }

            // ходит Игрок2 (машина)
            int cnt = 0;
            do {
                Turn.getComputerTurn(cTurnXY, hTurnXY, field_size, difficulty, cnt);
                cnt++;
            }
            while(!Map.isCellValid(cTurnXY, map));
            Map.updateMap(cTurnXY, map, CELLS[0]);
            System.out.printf("Компьютер выбрал ячейку: %3d, %3d\n", (cTurnXY[0]+1), (cTurnXY[1]+1));
            turn++;
            if ( turn/2 >= winingStreakSize &&
                    Game.isWin(winingStreakSize, cTurnXY, map) )
            {
                winner = CELLS[0];
                break;
            }
            else if ( (int)turn >= max_turns ){
                break;
            }
        }

        Map.printMap(map);
        if (!Map.isCellEmpty(winner)){
            System.out.println("Победа. Победитель: " + winner);
        }
        else {
            System.out.println("Не осталось возможных ходов. Ничья.");
        }
    }

}
