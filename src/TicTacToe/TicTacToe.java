package TicTacToe;

/**
 * @description Simple Tic-Tac-Toe training game with GUI on Java in OO-style
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import TicTacToe.game.Game;

/*
TODO:
[ ] help menu
[ ] about menu
[ ] game statistic display:
    [ ] current turn / max turns
    [ ] winning streak size
  */

public class TicTacToe {
  public static void main(String[] args) {
    Game tttGame = new Game();

    tttGame.initGame();
  }
}
