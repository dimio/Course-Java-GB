package TicTacToe.game;

/**
 * @description Tic-Tac-Toe GUI board action buttons
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import javax.swing.*;

class GameButton extends JButton {
  private int rowNum, colNum;
  private GameBoard board;

  GameButton(int buttonIndex, GameBoard currentGameBoard) {
    this.board = currentGameBoard;

    rowNum = buttonIndex / board.getGameSettings().getBoardWidth(); //i
    colNum = buttonIndex % board.getGameSettings().getBoardWidth(); //j

    setSize(board.getGameSettings().getCellSize() - 5,
        board.getGameSettings().getCellSize() - 5);
    addActionListener(new GameActionListener(this));
  }

  GameBoard getBoard() {
    return board;
  }

  int getRowNum() {
    return rowNum;
  }

  int getColNum() {
    return colNum;
  }
}
