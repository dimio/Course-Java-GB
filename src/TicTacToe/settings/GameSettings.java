package TicTacToe.settings;

/**
 * @author Dmitry (dimio-blog@gmail.com)
 * @version 0.0.8
 * @description Game settings storage
 */

public class GameSettings {
  public static final String GAME_TITLE = "TicTacToe";
  public static final byte NULL_SYMBOL = '\u0000';
  public static final byte PLAYER_ONE_SIGN = 'X';
  public static final byte PLAYER_TWO_SIGN = 'O';
  public static final int MAX_BOARD_WIDTH = 20;
  public static final int MAX_BOARD_HEIGHT = 20;

  private int cellSize;
  private int boardHeight;
  private int boardWidth; //min 2x2
  private int winingStreakSize;
  private int gameMaxTurns;
  private int turnOrder; //0: player first, 1: comp first

  public GameSettings(int Width, int Height, int StreakSize, int Turn) {
    setBoardWidth(Width); //field j-size (x) columns
    setBoardHeight(Height); //field i-size (y) rows
    setWinningStreakSize(StreakSize);
    setTurnOrder(Turn);

    setGameMaxTurns();

    this.cellSize = 80;
  }

  public int getTurnOrder() {
    return turnOrder;
  }

  public int getGameMaxTurns() {
    return gameMaxTurns;
  }

  public int getWiningStreakSize() {
    return winingStreakSize;
  }

  public int getCellSize() {
    return cellSize;
  }

  public int getBoardHeight() {
    return boardHeight;
  }

  public int getBoardWidth() {
    return boardWidth;
  }

  public void setTurnOrder(int turnOrder) {
    this.turnOrder = (turnOrder == 0) ? 0 : 1;
  }

  public void setBoardWidth(int boardWidth) {
    if (boardWidth > 2){
      if (boardWidth <= MAX_BOARD_WIDTH){
        this.boardWidth = boardWidth;
      }
      else {
        this.boardWidth = MAX_BOARD_WIDTH;
      }
    }
    else {
      this.boardWidth = 2;
    }
  }

  public void setBoardHeight(int boardHeight) {
    if (boardHeight > 2){
      if (boardHeight <= MAX_BOARD_HEIGHT){
        this.boardHeight = boardHeight;
      }
      else {
        this.boardHeight = MAX_BOARD_HEIGHT;
      }
    }
    else {
      this.boardHeight = 2;
    }
  }

  public void setWinningStreakSize(int winingStreakSize) {
    if (winingStreakSize > 0 && (winingStreakSize <= Math.max(boardHeight, boardWidth))){
      this.winingStreakSize = winingStreakSize;
    }
    else {
      this.winingStreakSize = Math.min(boardHeight, boardWidth);
    }
  }

  private void setGameMaxTurns() {
    this.gameMaxTurns = this.boardHeight * this.boardWidth; //i*j
  }
}
