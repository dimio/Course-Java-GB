package TicTacToeCli;

/**
 * @description Simple TicTacToe training game on Java in procedural style
 * @version 0.0.3
 * @author Dmitry (dimio-blog@gmail.com)
 */

import java.util.Scanner;

public class TacTacToe {
    /** НАСТРОЙКИ
     * @difficulty - сложность игры, возможные значения:
     * 1 - легко (случайные ходы -компьютера);
     * 2 - средне (предпочитаемый ход компьютера - рядом с ходом игрока);
     * 3 - тяжело (компьютер пытается выстроить победную серию или помешать игроку).
     */
    //TODO: запрашивать настройки при старте игры или как параметры запуска
    public static final int difficulty = 3;

    public static void main(String[] args) {
        int field_size;
        int[] hTurnXY = new int[2];
        int[] cTurnXY = new int[2];
        char[] CELLS = {'O', 'X', '_'};
        char[] hasWon = {CELLS[2]}; //в начале игры победителя нет
        float turn = 0;

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
            if (Game.isGameOver(turn, max_turns, winingStreakSize, hTurnXY, 'X', hasWon, map)){
                break;
            }

            // ходит Игрок2 (машина)
            int cnt = 0;
            do {
                Turn.getComputerTurn(cTurnXY, hTurnXY, winingStreakSize, difficulty, cnt, CELLS, map);
                cnt++;
            }
            while(!Map.isCellValid(cTurnXY, map));
            Map.updateMap(cTurnXY, map, CELLS[0]);
            System.out.printf("Компьютер выбрал ячейку: %3d, %3d\n", (cTurnXY[0]+1), (cTurnXY[1]+1));
            turn++;
            if (Game.isGameOver(turn, max_turns, winingStreakSize, cTurnXY, 'O', hasWon, map)){
                break;
            }
        }

        // игра окончена тем или иным образом
        Map.printMap(map);
        if (!Map.isCellEmpty(hasWon[0])){
            System.out.println("Победа. Победитель: " + hasWon[0]);
        }
        else {
            System.out.println("Не осталось возможных ходов. Ничья.");
        }
    }

}
