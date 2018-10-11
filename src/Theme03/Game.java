package Theme03_TicTacToe;

/**
 * @description Simple game win checking
 * @version 0.0.3
 * @author Dmitry (dimio-blog@gmail.com)
 */

public class Game {
    public static boolean isGameOver(float turn, int maxTurns, int winingStreakSize,
                                     int[] turnXY, char winner, char[] hasWon, char[][] map)
    {
        boolean result = false;

        if ( (int)Math.ceil(turn/2) >= winingStreakSize &&
                isWin(winingStreakSize, turnXY, map) )
        {
            hasWon[0] = winner;
            result = true;
        }
        else if ( (int)turn >= maxTurns ){
            result = true;
        }

        return result;
    }

    //TODO: переделать, должен получить вес и координаты хода, вернуть результат сверки с winingStreakSize
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
        if ( _isRowWin(winingStreakSize, j, map[i]) ||
                _isColumnWin(winingStreakSize, map, i, j) )
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

    //TODO: тогда эти можно будет вообще убрать
    private static boolean _isRowWin(int winingStreakSize, int j, char[] row){
        boolean result = false;

        if (Engine.inRowStreakSize(j, row) >= winingStreakSize){
            result = true;
        }

        return result;
    }

    private static boolean _isColumnWin(int winingStreakSize, char[][] map, int i, int J){
        boolean result = false;

        if (Engine.inColumnStreakSize(i, J, map) >= winingStreakSize){
            result = true;
        }

        return result;
    }

    // LtR - Left to Right
    private static boolean _isDiagonalWin_LtR(int winingStreakSize, int i, int j, char[][] map){
        boolean result = false;

        if (Engine.inDiagonalStreakSize_LtR(i, j, map) >= winingStreakSize){
            result = true;
        }

        return result;
    }

    // RtL - Right to Left
    private static boolean _isDiagonalWin_RtL(int winingStreakSize, int i, int j, char[][] map){
        boolean result = false;

        if (Engine.inDiagonalStreakSize_RtL(i, j, map) >= winingStreakSize){
            result = true;
        }

        return result;
    }

    /*public static boolean isStandoff(char[][] map, char emptyCell){
        return Map.isFull(map, emptyCell); //не нужен, удалён
    }*/
}
