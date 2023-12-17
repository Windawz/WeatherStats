package com.nmihalchenko.weatherstats;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class WeatherStatsWindow {
    private JTextField cityNameTextField;
    private JTable statsTable;
    private JButton getStatisticsButton;
    private JPanel rootPanel;

    public WeatherStatsWindow(StatsSource source) {
        getStatisticsButton.addActionListener(actionEvent -> {
            String cityName = cityNameTextField.getText().trim();
            var stats = source.getStats(cityName);

            TableModel tableModel = new AbstractTableModel() {
                @Override
                public int getRowCount() {
                    return stats.size() > 0
                            ? stats.get(0).size() - 1
                            : 0;
                }

                @Override
                public int getColumnCount() {
                    return stats.size();
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    return stats.get(columnIndex).get(rowIndex + 1);
                }

                @Override
                public String getColumnName(int column) {
                    return stats.get(column).get(0);
                }
            };

            statsTable.setModel(tableModel);
        });
    }

    public static void main(String[] args) {
        StatsSource source = null;
        try {
            source = new MysqlStatsSource("jdbc:mysql://localhost/weatherstatsdb?user=weatherstatsapp&password=weatherstatsapppassword");
            var frame = new JFrame();
            frame.setContentPane(new WeatherStatsWindow(source).rootPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            if (source != null) {
                source.close();
                source = null;
            }
            throw e;
        }
    }
}
