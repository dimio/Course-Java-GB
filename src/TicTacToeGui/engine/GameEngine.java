package TicTacToeGui.engine;

/**
 * @description Calculator for computer turn coordinates
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import TicTacToeGui.game.GameBoard;
import TicTacToeGui.settings.GameSettings;

import java.util.Random;

public class GameEngine {

  public class TurnCoord {
    private final int i, j;

    TurnCoord(int i, int j) {
      this.i = i;
      this.j = j;
    }

    public int getI() {
      return i;
    }
    public int getJ() {
      return j;
    }
  }

  private GameBoard board;
  private GameSettings settings;
  private byte realPlayerSign, computerPlayerSign;
  private byte[][] map;
  private static Random random = new Random();
  private boolean hasWinTurn = false; //выигрышный ход для компьютера

  public GameEngine(GameBoard board) {
    this.board = board;
    settings = board.getGameSettings();
    map = board.getGameField();

    if (board.getGame().getGamePlayers()[0].isRealPlayer()){
      this.realPlayerSign = board.getGame().getGamePlayers()[0].getPlayerSign();
      this.computerPlayerSign = board.getGame().getGamePlayers()[1].getPlayerSign();
    }
    else {
      this.realPlayerSign = board.getGame().getGamePlayers()[1].getPlayerSign();
      this.computerPlayerSign = board.getGame().getGamePlayers()[0].getPlayerSign();
    }
  }

  public boolean getHasWinTurn() {
    return hasWinTurn;
  }

  public TurnCoord makeIsFirstTurn() {
    int i = settings.getBoardHeight() / 2;
    int j = settings.getBoardWidth() / 2;

    return new TurnCoord(i, j);
  }

  public GameEngine.TurnCoord makeStupidTurn() {
    GameEngine.TurnCoord turnCoord;

    int[] turnWinC = new int[3]; //вес, координаты
    int turnSizeC;

    int[] turnWinH = new int[3]; //вес, координаты
    int turnSizeH;

    // обойти поле, сходить в каждую пустую клетку и попробовать найти выигрышный ход
    for (int i = 0; i < settings.getBoardHeight(); i++){
      for (int j = 0; j < settings.getBoardWidth(); j++){

        if (board.isTurnable(i, j)){
          map[i][j] = computerPlayerSign; //пометить для хода компьютера
          // посчитать вес хода (=наибольшей длине цепочки от данной клетки)
          turnSizeC = inCellMaxStreakSize(i, j);
          // если в строке есть победный ход - сохранить и закончить подбор
          if (turnSizeC >= settings.getWiningStreakSize()){
            hasWinTurn = true; 
            turnWinC[0] = turnSizeC;
            turnWinC[1] = i;
            turnWinC[2] = j;
            map[i][j] = GameSettings.NULL_SYMBOL; //пометить обратно на пустую
            break;
          }
          // иначе - сравнить с сохраненным наилучшим ходом
          else if (turnSizeC > turnWinC[0]){
            turnWinC[0] = turnSizeC;
            turnWinC[1] = i;
            turnWinC[2] = j;
          }
          // если веса ходов равны - случайным образом решить, обновить ли лучший ход
          else if ((turnSizeC == turnWinC[0]) && (random.nextInt(10) >= 3)){
            turnWinC[0] = turnSizeC;
            turnWinC[1] = i;
            turnWinC[2] = j;
          }

          // посчитать вес хода человека, если надо - обновить максимальный
          map[i][j] = realPlayerSign; //пометить для хода человека
          turnSizeH = inCellMaxStreakSize(i, j);
          if (turnSizeH > turnWinH[0]){
            turnWinH[0] = turnSizeH;
            turnWinH[1] = i;
            turnWinH[2] = j;
          }

          map[i][j] = GameSettings.NULL_SYMBOL; //пометить обратно на пустую
        }
      }
      if (hasWinTurn){ //внутри метода надо через его же геттер получать или нет?
        break; // если есть победный ход компьютера - дальше искать не надо
      }
    }

    // если есть победный ход или ход лучше соперника - сходить
    if (hasWinTurn || turnWinC[0] > turnWinH[0]){
      turnCoord = new GameEngine.TurnCoord(turnWinC[1], turnWinC[2]); //i,j
    }
    // если у соперника лучше - попытаться сорвать его серию
    else {
      turnCoord = new GameEngine.TurnCoord(turnWinH[1], turnWinH[2]); //i,j
    }

    return turnCoord;
  }

  public int inCellMaxStreakSize(int i, int j) {
    /*
     * общий алгоритм - последовательно проверить клетки в стороны от переданной,
     * если каждаю последующая клетка в проверке НЕ пустая и её символ
     * совпал с символом текущей клетки - увеличить длину возможной выигрышной серии,
     * затем найти максимальную серии среди возможных (ряд/столбец/диагонали)
     */
    int[] sizes = {
        inRowStreakSize(i, j),
        inColumnStreakSize(i, j),
        inDiagonalStreakSize_LtR(i, j),
        inDiagonalStreakSize_RtL(i, j),
    };

    int maxElNum = 0;
    for (int k = 1; k < sizes.length; k++){
      //maxElNum = (sizes[k] > sizes[maxElNum]) ? k : maxElNum;
      if (sizes[k] > sizes[maxElNum]) maxElNum = k;
    }

    return sizes[maxElNum];
  }

  private int inRowStreakSize(int I, int j) {
    /** I - номер строки, он неизменен внутри данного метода
     *  перемещение по стобцам строки - счетчик j(k)
     */
    int result = 1;

    int cnt = 0; //один такт счетчика = двум совпавшим подряд клеткам
    byte cell; //стартовая клетка - куда был сделан ход
    int k = j;

    //число совпавших от стартовой вправо
    // подсчет границы тоже вынести потом в однократный перед циклом?
    while (j < settings.getBoardWidth() - 1){
      cell = map[I][j];
      if (cell == map[I][j + 1]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
      }
      else {
        break;
      }
      j++;
    }

    //число совпавших от стартовой клетки влево
    while (k > 0){
      cell = map[I][k];
      if (cell == map[I][k - 1]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
      }
      else {
        break;
      }
      k--; //j
    }

    result += cnt;

    return result;
  }

  private int inColumnStreakSize(int i, int J) {
    int result = 1;

    /** J - номер столбца, он неизменен внутри данного метода
     *  перемещение по строкам столбца - счетчик i(k)
     */
    int cnt = 0; //один такт счетчика = двум совпавшим подряд клеткам
    byte cell; //стартовая клетка - куда был сделан ход
    int k = i;

    //число совпавших от стартовой наверх
    while (i > 0){
      cell = map[i][J];
      if (cell == map[i - 1][J]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
      }
      else {
        break;
      }
      i--;
    }

    //число совпавших от стартовой вниз
    while (k < settings.getBoardHeight() - 1){
      cell = map[k][J];
      if (cell == map[k + 1][J]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
      }
      else {
        break;
      }
      k++;
    }

    result += cnt;

    return result;
  }

  private int inDiagonalStreakSize_LtR(int i, int j) {
    int result = 1; //одно совпадение есть всегда - это текущая клетка

    byte cell;
    int cnt = 0;
    int k = i;
    int m = j;

    //пока находимся в границах поля - проверяем диагональ (левая часть)
    while (k > 0 && m > 0){
      cell = map[k][m];
      if (cell == map[k - 1][m - 1]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
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
    while (i < settings.getBoardHeight() - 1 && j < settings.getBoardWidth() - 1){
      cell = map[i][j];
      if (cell == map[i + 1][j + 1]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
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

  private int inDiagonalStreakSize_RtL(int i, int j) {
    int result = 1; //одно совпадение есть всегда - это текущая клетка

    byte cell;
    int cnt = 0;
    int k = i;
    int m = j;

    //пока находимся в границах поля - проверяем диагональ (левая часть)
    while (k < settings.getBoardHeight() - 1 && m > 0){
      cell = map[k][m];
      if (cell == map[k + 1][m - 1]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
      }
      else {
        break;
      }
      k++; //i
      m--; //j
    }

    // правая часть
    while (i > 0 && j < settings.getBoardWidth() - 1){
      cell = map[i][j];
      if (cell == map[i - 1][j + 1]){
        cnt++;
        if (cnt >= settings.getWiningStreakSize()) break;
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
