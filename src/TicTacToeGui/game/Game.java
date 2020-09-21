package TicTacToeGui.game;

/**
 * @description Simple Tic-Tac-Toe game class
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import TicTacToeGui.settings.GameSettings;

import javax.swing.*;

public class Game {
  private GameBoard board;
  private GamePlayer[] gamePlayers = new GamePlayer[2];
  private GameSettings settings;
  private int playersTurn;
  private int generalTurn;

  public Game() {
  }

  public void newGame() {
    board.getField().dispose();

    initGame();

    if (settings.getTurnOrder() == 1){
      board.getButton(0, 0).doClick();
    }
  }

  public void initGame() {
    playersTurn = 0;
    generalTurn = 1;

    if (settings == null){
      settings = new GameSettings(3, 3, 3, 0); //поле 3х3, победная серия 3
    }
    else {
      settings = new GameSettings(
          settings.getBoardWidth(),
          settings.getBoardHeight(),
          settings.getWiningStreakSize(),
          settings.getTurnOrder()
      );
    }

    if (settings.getTurnOrder() == 0){
      gamePlayers[0] = new GamePlayer(GameSettings.PLAYER_ONE_SIGN, true);
      gamePlayers[1] = new GamePlayer(GameSettings.PLAYER_TWO_SIGN, false);
    }
    else {
      gamePlayers[0] = new GamePlayer(GameSettings.PLAYER_ONE_SIGN, false);
      gamePlayers[1] = new GamePlayer(GameSettings.PLAYER_TWO_SIGN, true);
    }

    this.board = new GameBoard(this);
  }

  GameSettings getSettings() {
    return settings;
  }

  GamePlayer getCurrentPlayer() {
    return gamePlayers[playersTurn];
  }

  public GamePlayer[] getGamePlayers() {
    return gamePlayers;
  }

  int getGeneralTurn() {
    return generalTurn;
  }

  void showMessage(String messageText) {
    JOptionPane.showMessageDialog(board, messageText);
  }

  void passTurn() {
    if (playersTurn == 0){
      playersTurn = 1;
      incrementGeneralTurn();
    }
    else {
      playersTurn = 0;
      incrementGeneralTurn();
    }
  }

  private void incrementGeneralTurn() {
    generalTurn++;
  }
}