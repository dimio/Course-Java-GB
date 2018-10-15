package Theme03;

/**
 * @description Simple tic-tac-toe engine
 * @version 0.0.3
 * @author Dmitry (dimio-blog@gmail.com)
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
        if (cnt <= 30){
            turnXY[0] = hTurnXY[0] + random.nextInt(3)-1;
            turnXY[1] = hTurnXY[1] + random.nextInt(3)-1;
        }
        else {
            makeSillyTurn(turnXY, field_size);
        }
    }

    //TODO: переделать isWin в пакете Game, модифицировать метод выбора хода (с учетом длин диагоналей)
    //TODO: добавить выбор случайного хода из лучших (иначе ходы компьютера предсказуемы)
    //TODO: для ходов равного веса добавить рандомизацию обновления turnWinC
    public static void makeStupidTurn(int[] turnXY, int winingStreakSize, char[] CELLS, char[][] map){
        boolean hasWonC = false; //выигрышный ход для компьютера
        int[] turnWinC = new int[3]; //вес, координаты
        int turnSizeC;

        int[] turnWinH = new int[3]; //вес, координаты
        int turnSizeH;

        // обойти поле, сходить в каждую пустую клетку и попробовать найти выигрышный ход
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map.length; j++){

                if ( Map.isCellEmpty(map[i][j]) ){
                    map[i][j] = CELLS[0]; //пометить для хода компьютера
                    // посчитать вес хода (=наибольшей длине цепочки от данной клетки)
                    turnSizeC = inCellMaxStreakSize(i, j, map);
                    // если в строке есть победный ход - сохранить и закончить подбор
                    if ( turnSizeC >=  winingStreakSize){
                        hasWonC = true;
                        turnWinC[0] = turnSizeC;
                        turnWinC[1] = i;
                        turnWinC[2] = j;
                        map[i][j] = CELLS[2]; //пометить обратно на пустую
                        break;
                    }
                    // иначе - сравнить с сохраненным наилучшим ходом
                    else if (turnSizeC > turnWinC[0]){
                        turnWinC[0] = turnSizeC;
                        turnWinC[1] = i;
                        turnWinC[2] = j;
                    }

                    // посчитать вес хода человека, если надо - обновить максимальный
                    map[i][j] = CELLS[1]; //пометить для хода человека
                    turnSizeH = inCellMaxStreakSize(i, j, map);
                    if (turnSizeH > turnWinH[0]){
                        turnWinH[0] = turnSizeH;
                        turnWinH[1] = i;
                        turnWinH[2] = j;
                    }

                    map[i][j] = CELLS[2]; //пометить обратно на пустую
                }
            }
            if (hasWonC){
                break; // если есть победный ход компьютера - сходить
            }
        }

        // в принципе условие избыточное, т.к. победный ход имеет max вес (тогда turnXY присваивать в цикле подбора)
        //if (!hasWonC) {
        // сравнить по весу лучший ход компьютера и человека,
        // если у человека лучше - попытаться сорвать серию
        if (turnWinC[0] < turnWinH[0]) {
            turnXY[0] = turnWinH[1];
            turnXY[1] = turnWinH[2];
        }
        else {
            turnXY[0] = turnWinC[1];
            turnXY[1] = turnWinC[2];
        }
        //}
    }

    public static int inCellMaxStreakSize(int i, int j, char[][] map){
        int max = 0;

        int[] sizes = {
                inRowStreakSize(j, map[i]),
                inColumnStreakSize(i, j, map),
                inDiagonalStreakSize_LtR(i, j, map),
                inDiagonalStreakSize_RtL(i, j, map),
        };

        for (int size: sizes) {
            max = (size > max) ? size : max;
        }

        return max;
    }

    public static int inRowStreakSize(int j, char[] row){
        int result = 1;

        int cnt = 0; //один такт счетчика = двум совпавшим подряд клеткам
        char cell; //стартовая клетка - куда был сделан ход
        int k = j;

        //число совпавших от стартовой вправо
        // подсчет границы тоже вынести потом в однократный перед циклом
        while (j < row.length-1){
            cell = row[j];
            if (cell == row[j+1] && !Map.isCellEmpty(row[j+1])){
                cnt++;
            } else {
                //result += cnt;
                break;
            }
            j++;
        }

        //число совпавших от стартовой клетки влево
        while (k > 0){
            cell = row[k];
            if (cell == row[k-1] && !Map.isCellEmpty(row[k-1])){
                cnt++;
            } else {
                break;
            }
            k--; //j
        }

        result += cnt;

        return result;
    }

    public static int inColumnStreakSize(int i, int J, char[][] map){
        int result = 1;

        /** J - номер столбца, он неизменен внутри данного метода
         *  перемещение по строкам столбца - счетчик i
         */
        int cnt = 0; //один такт счетчика = двум совпавшим подряд клеткам
        char cell; //стартовая клетка - куда был сделан ход
        int k = i;

        //число совпавших от стартовой наверх
        while (i > 0){
            cell = map[i][J];
            if (!Map.isCellEmpty(map[i-1][J]) && cell == map[i-1][J]){
                cnt++;
            } else {
                break;
            }
            i--;
        }

        //число совпавших от стартовой вниз
        while (k < map.length-1){
            cell = map[k][J];
            if (cell == map[k+1][J] && !Map.isCellEmpty(map[k+1][J])){
                cnt++;
            } else {
                break;
            }
            k++;
        }

        result += cnt;

        return result;
    }

    public static int inDiagonalStreakSize_LtR(int i, int j, char[][] map){
        int result = 1; //одно совпадение есть всегда - это текущая клетка

        char cell;
        int cnt = 0;
        int k = i;
        int m = j;

        //пока находимся в границах поля - проверяем диагональ (левая часть)
        while (k > 0 && m > 0){
            cell = map[k][m];
            if (cell == map[k-1][m-1] && !Map.isCellEmpty(map[k-1][m-1])){
                cnt++;
            }
            else {
                break;
            }
            k--; //i
            m--; //j
        }

        // правая часть
        // подсчет границы вынести в однократный перед циклом? (или компилятор сам оптимизирует
        // и выносить нет смысла? уточнить.
        while (i < map.length-1&& j < map.length-1){
            cell = map[i][j];
            if (cell == map[i+1][j+1] && !Map.isCellEmpty(map[i+1][j+1])){
                cnt++;
            }
            else {
                break;
            }
            i++;
            j++;
        }

        result += cnt;

        return result; //return ctn+1
    }

    public static int inDiagonalStreakSize_RtL(int i, int j, char[][] map){
        int result = 1; //одно совпадение есть всегда - это текущая клетка

        char cell;
        int cnt = 0;
        int k = i;
        int m = j;

        //пока находимся в границах поля - проверяем диагональ (левая часть)
        while (k < map.length-1 && m > 0){
            cell = map[k][m];
            if (cell == map[k+1][m-1] && !Map.isCellEmpty(map[k+1][m-1])){
                cnt++;
            }
            else {
                break;
            }
            k++; //i
            m--; //j
        }

        // правая часть
        while (i > 0 && j < map.length-1){
            cell = map[i][j];
            if (cell == map[i-1][j+1] && !Map.isCellEmpty(map[i-1][j+1])){
                cnt++;
            }
            else {
                break;
            }
            i--;
            j++;
        }

        result += cnt;

        return result;
    }
}
