package TicTacToe.game;

/**
 * @author Dmitry (dimio-blog@gmail.com)
 * @version 0.0.8
 * @description Tic-Tac-Toe game Player builder
 */

class GamePlayer {
  private char playerSign;
  private boolean realPlayer;

  GamePlayer(char playerSign, boolean realPlayer) {
    this.realPlayer = realPlayer;
    this.playerSign = playerSign;
  }

  boolean isRealPlayer() {
    return realPlayer;
  }

  char getPlayerSign() {
    return playerSign;
  }
}
