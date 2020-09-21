package TicTacToeGui.game;

/**
 * @description Handler for a Game field buttons clicks
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionListener implements ActionListener {
  private int row, col;
  private GameButton button;
  private GameBoard board;

  GameActionListener(GameButton button) {
    this.button = button;
    this.row = button.getRowNum(); //i
    this.col = button.getColNum(); //j
    this.board = button.getBoard();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (board.getGame().getCurrentPlayer().isRealPlayer()){
      updateByPlayerData();
    }
    else {
      updateByAIData();
    }

    if (board.getGame().getGeneralTurn() > board.getGameSettings().getGameMaxTurns()){
      board.getGame().showMessage("Ничья!");
      board.freezeField();
    }
  }

  private void updateByAIData() {
    board.makeTurnXY();
    board.updateGameField(board.getTurnY(), board.getTurnX()); //y=i, x=j

    GameButton button = board.getButton(board.getTurnY(), board.getTurnX());
    button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
    button.setEnabled(false);

    // если компьютер делает победный ход на соответсвтующей сложности
    if (board.isComputerWin()){
      button.getBoard().getGame().showMessage("Компьютер выиграл!");
      board.freezeField();
    }
        /*
        // иначе проверить победную серию для текущего хода компьютера
        else if (board.checkWin(row, col)){
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.freezeField();
        }
        */
    else {
      board.getGame().passTurn();
    }
  }

  private void updateByPlayerData() {
    board.updateGameField(row, col);

    button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
    button.setEnabled(false);

    if (board.checkWin(row, col)){
      button.getBoard().getGame().showMessage("Вы выиграли!");
      board.freezeField();
    }
    else {
      board.getGame().passTurn();
      updateByAIData();
    }
  }
}
