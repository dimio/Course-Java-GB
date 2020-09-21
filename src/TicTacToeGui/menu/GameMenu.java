package TicTacToeGui.menu;

/**
 * @description Game main menu builder
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import TicTacToeGui.game.GameBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JMenuBar {
  private GameSettingsMenu hasSettingsMenu;
  private GameBoard board;

  public GameMenu(GameBoard board) {
    this.board = board;

    JMenu mainMenu = new JMenu("Игра");

    JMenuItem newGame = new JMenuItem("Новая игра");
    newGame.addActionListener(new newGameButton());
    mainMenu.add(newGame);

    JMenuItem settingsMenu = new JMenuItem("Настройки");
    settingsMenu.addActionListener(new settingsMenuButton());
    mainMenu.add(settingsMenu);

    mainMenu.addSeparator();

    JMenuItem exitGame = new JMenuItem("Выход");
    exitGame.addActionListener(new exitGameButton());
    mainMenu.add(exitGame);

    JMenu helpMenu = new JMenu("Справка");

    JMenuItem help = new JMenuItem("Справка");
    helpMenu.add(help);

    JMenuItem about = new JMenuItem("О программе");
    helpMenu.add(about);

    add(mainMenu);
    add(helpMenu);
  }

  private class settingsMenuButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (hasSettingsMenu == null){
        hasSettingsMenu = new GameSettingsMenu(board, board.getGameSettings());
      }
      else {
        hasSettingsMenu.updateSettingsMenu();
        hasSettingsMenu.setVisible(true);
      }
    }
  }

  private class exitGameButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }

  private class newGameButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      board.getGame().newGame();
    }
  }
}
