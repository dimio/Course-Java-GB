package TicTacToe.menu;

/**
 * @description Game settings menu builder
 * @version 0.0.8
 * @author Dmitry (dimio-blog@gmail.com)
 */

import TicTacToe.settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameSettingsMenu extends JDialog {
  private JTextField boardHeightInput, boardWidthInput, winingStreakSizeInput;
  private JComboBox<String> turnOrderBox;
  private GameSettings settings;

  GameSettingsMenu(JFrame owner, GameSettings settings) {
    super(owner, "Настройки", true);

    this.settings = settings;

    boardHeightInput = new JTextField();
    boardHeightInput.setHorizontalAlignment(JTextField.CENTER);

    boardWidthInput = new JTextField();
    boardWidthInput.setHorizontalAlignment(JTextField.CENTER);

    winingStreakSizeInput = new JTextField();
    winingStreakSizeInput.setHorizontalAlignment(JTextField.CENTER);

    String[] turnOrderList = {"Игрок", "Компьютер"};
    turnOrderBox = new JComboBox<>(turnOrderList);

    updateSettingsMenu();

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5, 2));

    JLabel boardH = new JLabel("Высота поля (строки):");
    boardH.setToolTipText("От 2 до " + GameSettings.MAX_BOARD_HEIGHT);
    panel.add(boardH);
    panel.add(boardHeightInput);

    JLabel boardW = new JLabel("Ширина поля (столбцы):");
    boardW.setToolTipText("От 2 до " + GameSettings.MAX_BOARD_WIDTH);
    panel.add(boardW);
    panel.add(boardWidthInput);

    JLabel size = new JLabel("Размер победной серии:");
    size.setToolTipText("Размер выигрышного количества одинаковых символов в строке, столбце или диагонали. "
        + "От 1 до максимального измерения поля, иначе будет установлен равным минимальному измерению поля");
    panel.add(size);
    panel.add(winingStreakSizeInput);

    panel.add(new JLabel("Порядок первого хода:"));
    panel.add(turnOrderBox);

    JButton saveButton = new JButton("Сохранить");
    saveButton.addActionListener(new SaveButton());
    saveButton.setToolTipText("Для вступления настроек в силу нобходимо начать новую игру");

    JButton cancelButton = new JButton("Отмена");
    cancelButton.addActionListener(new CancelButton());

    panel.add(saveButton);
    panel.add(cancelButton);

    add(panel);
    setSize(350, 250);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  void updateSettingsMenu() {
    //TODO: проверку и фильтрацию ввода можно будет встроить сюда, как в калькуляторе
    boardHeightInput.setText(Integer.toString(settings.getBoardHeight()));
    boardWidthInput.setText(Integer.toString(settings.getBoardWidth()));
    winingStreakSizeInput.setText(Integer.toString(settings.getWiningStreakSize()));
    turnOrderBox.setSelectedIndex(settings.getTurnOrder());
  }

  private class SaveButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      settings.setBoardHeight(Integer.parseInt(boardHeightInput.getText()));
      settings.setBoardWidth(Integer.parseInt(boardWidthInput.getText()));
      settings.setWinningStreakSize(Integer.parseInt(winingStreakSizeInput.getText()));
      settings.setTurnOrder(turnOrderBox.getSelectedIndex());
      setVisible(false);
    }
  }

  private class CancelButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      setVisible(false);
    }
  }
}
