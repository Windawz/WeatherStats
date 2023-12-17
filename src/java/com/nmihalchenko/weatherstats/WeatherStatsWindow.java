package com.nmihalchenko.weatherstats;

import javax.swing.*;

public class WeatherStatsWindow {
    private JTextField textField1;
    private JTable table1;
    private JButton getStatisticsButton;
    private JPanel rootPanel;

    public static void main(String[] args) {
        var frame = new JFrame();
        frame.setContentPane(new WeatherStatsWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
