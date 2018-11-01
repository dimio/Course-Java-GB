package TicTacToe.game;

/**
 * @description Tic-Tac-Toe field map and GUI board builder
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import TicTacToe.menu.GameMenu;
import TicTacToe.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {
  private char[][] gameField;
  private int turnX, turnY;
  private GameButton[] gameButtons;
  private Game game;
  private GameEngine engine;
  private GameSettings settings;
  private JFrame field;

  GameBoard(Game currentGame) {
    this.game = currentGame;
    this.settings = game.getSettings();

    initField();

    this.engine = new GameEngine(this);
  }

  public Game getGame() {
    return game;
  }

  // TODO: DEL потом убрать, получать напрямую из Game
  public GameSettings getGameSettings() {
    return settings;
  }
  // DEL

  GameButton getButton(int i, int j) {
    int buttonIndex = settings.getBoardWidth() * i + j;
    return gameButtons[buttonIndex];
  }

  JFrame getField() {
    return field;
  }

  // TODO: DEL
  int getTurnX() {
    return turnX;
  }

  int getTurnY() {
    return turnY;
  }

  // DEL
  char[][] getGameField() {
    return gameField;
  }

  void makeTurnXY() {
    GameEngine.TurnCoord turnIJ;

    if (settings.getTurnOrder() == 1 && game.getGeneralTurn() == 1){
      turnIJ = engine.makeIsFirstTurn();
    }
    //TODO: здесь выбирать метод получения координат хода в зависимости от настройки сложности
    else {
      turnIJ = engine.makeStupidTurn();
    }

    turnX = turnIJ.getJ(); //j
    turnY = turnIJ.getI(); //i
  }

  void freezeField() {
    for (GameButton gameButton : gameButtons){
      gameButton.setEnabled(false);
    }
  }

  void updateGameField(int i, int j) {
    gameField[i][j] = game.getCurrentPlayer().getPlayerSign();
  }

  boolean isComputerWin() {
    return engine.getHasWinTurn();
  }

  boolean checkWin(int i, int j) {
    return engine.inCellMaxStreakSize(i, j) >= settings.getWiningStreakSize();
  }

  boolean isTurnable(int i, int j) {
    return gameField[i][j] == GameSettings.NULL_SYMBOL;
  }

  private void initField() {
    this.field = new JFrame();

    field.setTitle(GameSettings.GAME_TITLE);
    field.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // TODO: сделать квадратные клетки, сетка д.б. в контейнере с прокруткой
    field.setPreferredSize(new Dimension(settings.getCellSize() * settings.getBoardWidth(),
        settings.getCellSize() * settings.getBoardHeight()));

    field.setJMenuBar(new GameMenu(this));

    JPanel gameFieldPanel = new JPanel();
    // число строк, число столбцов
    gameFieldPanel.setLayout(new GridLayout(settings.getBoardHeight(), settings.getBoardWidth()));

    gameFieldPanel.setSize(settings.getCellSize() * settings.getBoardWidth(),
        settings.getCellSize() * settings.getBoardHeight());

    gameField = new char[settings.getBoardHeight()][settings.getBoardWidth()];

    gameButtons = new GameButton[settings.getBoardHeight() * settings.getBoardWidth()];

    for (int i = 0; i < gameButtons.length; i++){
      gameButtons[i] = new GameButton(i, this);
      gameFieldPanel.add(gameButtons[i]);
    }

    field.getContentPane().add(gameFieldPanel);

    field.setVisible(true);
    field.pack();
  }
}
