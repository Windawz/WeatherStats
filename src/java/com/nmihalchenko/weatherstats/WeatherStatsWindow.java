package com.nmihalchenko.weatherstats;

import javax.swing.*;

public class WeatherStatsWindow {
    private JTextField cityNameTextField;
    private JTable statsTable;
    private JButton getStatisticsButton;
    private JPanel rootPanel;

    public WeatherStatsWindow(StatsSource source) {

        getStatisticsButton.addActionListener(actionEvent -> {
            String cityName = cityNameTextField.getText().trim();
            var stats = source.getStats(cityName);

            var tableModel = statsTable.getModel();

            int rowIndex = 1;
            for (var key : stats.keySet()) {
                var value = stats.get(key);
                tableModel.setValueAt(key, rowIndex, 1);
                tableModel.setValueAt(value, rowIndex, 2);
                rowIndex++;
            }
        });
    }

    public static void main(String[] args) {
        var frame = new JFrame();
        frame.setContentPane(
                new WeatherStatsWindow(
                        new MysqlStatsSource(
                                "jdbc:mysql://localhost/weatherstatsdb?user=weatherstatsapp&password=weatherstatsapppassword")).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
