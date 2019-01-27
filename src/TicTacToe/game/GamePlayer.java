package TicTacToe.game;

/**
 * @author Dmitry (dimio-blog@gmail.com)
 * @version 0.0.8
 * @description Tic-Tac-Toe game Player builder
 */

public class GamePlayer {
  private byte playerSign;
  private boolean realPlayer;

  GamePlayer(byte playerSign, boolean realPlayer) {
    this.realPlayer = realPlayer;
    this.playerSign = playerSign;
  }

  public boolean isRealPlayer() {
    return realPlayer;
  }

  public byte getPlayerSign() {
    return playerSign;
  }
}
