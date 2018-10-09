package Theme03;

/**
 * @description Simple game win checking
 * @version 0.0.2
 * @author Dmitry
 */

public class Game {
    public static boolean isWin(int winingStreakSize, int[] turnXY, char[][] map){
        boolean result = false;
        int i = turnXY[0];
        int j = turnXY[1];
        int[] diagonalsLength; // [0] - LtR, [1] - RtL

        /**
         * Проверить ряд, столбец и диагонали,
         * в ячейку которых попадает сделанный ход
         */
        // если в ряду или столбце есть победная серия необходимой
        // длины - в игре достигнута победа
        if ( _isRowWin(winingStreakSize, map[i]) ||
                _isColumnWin(winingStreakSize, map, j) )
        {
            result = true;
        }
        // или проверить диагонали, если их длина позволяет вместить победную серию.
        else {
            diagonalsLength = Engine.getDiagonalsLength(i, j, map.length);

            // диагональ "лево-верх" - "право-низ" (Left to Right)
            if ( diagonalsLength[0] >= winingStreakSize &&
                    _isDiagonalWin_LtR(winingStreakSize, i, j, map) )
            {
                result = true;
            }
            // диагональ "право-верх" - "лево-низ" (Right to Left)
            else if ( diagonalsLength[1] >= winingStreakSize &&
                        _isDiagonalWin_RtL(winingStreakSize, i, j, map) )
            {
                result = true;
            }
        }

        return result;
    }

    // LtR - Left to Right
    private static boolean _isDiagonalWin_LtR(int winingStreakSize, int i, int j, char[][] map){
        boolean result = false;

        //один такт счетчика = двум совпавшим подряд клеткам
        int cnt = 1;
        //координата i для начальной ячейки диагонали проверки
        //TODO проблема с несрабатыванием проверки была в этом месте (выбор границы)
        //int i0 = (i+1 - winingStreakSize >= 0) ? i+1 - winingStreakSize : 0;
        int i0 = i - winingStreakSize;
        //координата j для начальной ячейки диагонали проверки
        //int j0 = (j+1 - winingStreakSize >= 0) ? j+1 - winingStreakSize : 0;
        int j0 = j - winingStreakSize;
        //начальная ячейка диагонали
        char cell;

        //пока находимся в границах поля - проверяем диагональ
        //while (i0 < map.length-1 && j0 < map.length-1){
        for (int k = 0; k <= winingStreakSize*2+1; k++){
            if (i0 >= map.length-1 && j0 >= map.length-1){
                break;
            }
            else if (i0 >= 0 && j0 >= 0){
                cell = map[i0][j0];

                // считаем число совпавших подряд клеток (текущая + последующая)
                if (cell == map[i0 + 1][j0 + 1] && !Map.isCellEmpty(map[i0 + 1][j0 + 1])) {
                    cnt++;
                } else {
                    cnt = 1;
                }

                if (cnt >= winingStreakSize) {
                    result = true;
                    break;
                }
            }

            i0++;
            j0++;
        }

        return result;
    }

    // RtL - Right to Left
    private static boolean _isDiagonalWin_RtL(int winingStreakSize, int i, int j, char[][] map){
        boolean result = false;

        //один такт счетчика = двум совпавшим подряд клеткам
        int cnt = 1;
        //координата i для начальной ячейки диагонали проверки
        int i0 = (i+1 - winingStreakSize >= 0) ? i+1 - winingStreakSize : 0;
        //координата j для начальной ячейки диагонали проверки
        int j0 = (j + winingStreakSize <= map.length-1) ? j + winingStreakSize : map.length-1;
        //начальная ячейка диагонали
        char cell;

        //пока находимся в границах поля - проверяем диагональ
        while (i0 < map.length-1 && j0 > 0){
            cell = map[i0][j0];

            // считаем число совпавших подряд клеток
            if (cell == map[i0+1][j0-1] && !Map.isCellEmpty(map[i0+1][j0-1])) {
                cnt++;
            } else {
                cnt = 1;
            }

            if (cnt >= winingStreakSize) {
                result = true;
                break;
            }

            i0++;
            j0--;
        }

        return result;
    }

    private static boolean _isColumnWin(int winingStreakSize, char[][] map, int J){
        boolean result = false;
        /** J - номер столбца, он неизменен внутри данного метода
         *  перемещение по строкам столбца - счетчик i
         */
        int cnt = 1; //один такт счетчика = двум совпавшим подряд клеткам
        char cell = map[0][J]; //первый символ в столбце

        for (int i=1; i < map.length; i++){
            // считаем число совпавших подряд клеток
            if ( cell == map[i][J] && !Map.isCellEmpty(map[i][J]) ){
                cnt++;
            }
            else {
                cnt = 1;
            }

            if (cnt >= winingStreakSize){
                result = true;
                break;
            }

            cell = map[i][J];
        }

        return result;
    }

    private static boolean _isRowWin(int winingStreakSize, char[] row){
        boolean result = false;

        int cnt = 1; //один такт счетчика = двум совпавшим подряд клеткам
        char cell = row[0]; //первый символ в строке

        for (int i=1; i < row.length; i++){
            // считаем число совпавших подряд клеток
            if ( cell == row[i] && !Map.isCellEmpty(row[i]) ){
                cnt++;
            }
            else {
                cnt = 1;
            }

            if (cnt >= winingStreakSize){
                result = true;
                break;
            }

            cell = row[i];
        }

        return result;
    }

    /** сейчас это просто обёртка для ничьей при заполнении карты.
     *  для логичного вызова из класса Game
     */
    /*public static boolean isStandoff(char[][] map, char emptyCell){
        return Map.isFull(map, emptyCell);
    }*/
}
