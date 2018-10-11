package Theme03_TicTacToe;

/**
 * @description Make, update and print a game field
 * @version 0.0.3
 * @author Dmitry (dimio-blog@gmail.com)
 */

public class Map {
    public static boolean isSizeCorrect(int size){
        //TODO: нужна обработка исключения при неверном формате size
        // (дробное или символ и т.п.)
        return size >= 2;
    }

    public static void updateMap(int[] turnXY, char[][] map, char usedCell){
        int i = turnXY[0];
        int j = turnXY[1];

        map[i][j] = usedCell;
    }

    public static boolean isCellValid(int[] turnXY, char[][] map){
        boolean result = true;
        int i = turnXY[0];
        int j = turnXY[1];

        if(i < 0 || i > map.length-1 || j < 0 || j > map.length-1) {
            result = false;
        }
        else if(!isCellEmpty(map[i][j])){
            result = false;
        }

        return result;
    }

    public static boolean isCellEmpty(char cell){
        //надо будет делать поле в дин. классе TicTakToe и брать оттуда
        char emptyCell = '_';

        return (cell == emptyCell);
    }

    public static void printMap(char[][] map) {
        //columns
        for(int i = 0; i < map.length; i++){
            //System.out.print(" ");
            System.out.printf("%3d", i+1);
        }
        System.out.println();

        //rows
        for(int i = 0; i < map.length; i++){
            System.out.print((i+1));

            for(int j = 0; j < map.length; j++){
                System.out.printf("%3c", map[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }

    public static char[][] initMap(int size, char emptyCell){
        char[][] newMap = new char[size][size];

        for(int i = 0; i < newMap.length; i ++){
            for(int j = 0; j < newMap.length; j++){
                newMap[i][j] = emptyCell;
            }
        }

        return newMap;
    }
}
