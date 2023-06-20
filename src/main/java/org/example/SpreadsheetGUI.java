import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpreadsheetGUI extends JFrame {
    private JTextField[][] cells;
    private JButton sumButton;
    private JButton averageButton;
    private JButton maxButton;
    private JButton minButton;

    public SpreadsheetGUI(int rows, int cols) {
        setTitle("电子表格");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cells = new JTextField[rows][cols];
        JPanel panel = new JPanel(new GridLayout(rows, cols));

        // 创建单元格文本框并添加到面板上
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new JTextField();
                panel.add(cells[i][j]);
            }
        }

        // 创建按钮并添加到面板上
        sumButton = new JButton("合计");
        averageButton = new JButton("平均");
        maxButton = new JButton("最大");
        minButton = new JButton("最小");

        panel.add(sumButton);
        panel.add(averageButton);
        panel.add(maxButton);
        panel.add(minButton);

        // 添加按钮的点击事件监听器
        sumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSum();
            }
        });

        averageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAverage();
            }
        });

        maxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateMax();
            }
        });

        minButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateMin();
            }
        });

        getContentPane().add(panel);
        pack();
        setVisible(true);
    }

    private void calculateSum() {
        double sum = 0;

        // 遍历单元格的值，并进行求和计算
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                String value = cells[i][j].getText();
                if (!value.isEmpty()) {
                    double cellValue = Double.parseDouble(value);
                    sum += cellValue;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "合计结果: " + sum);
    }

    private void calculateAverage() {
        double sum = 0;
        int count = 0;

        // 遍历单元格的值，并进行求和计算
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                String value = cells[i][j].getText();
                if (!value.isEmpty()) {
                    double cellValue = Double.parseDouble(value);
                    sum += cellValue;
                    count++;
                }
            }
        }

        double average = sum / count;
        JOptionPane.showMessageDialog(this, "平均结果: " + average);
    }

    private void calculateMax() {
        double max = Double.MIN_VALUE;

        // 遍历单元格的值，并找到最大值
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                String value = cells[i][j].getText();
                if (!value.isEmpty()) {
                    double cellValue = Double.parseDouble(value);
                    if (cellValue > max) {
                        max = cellValue;
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, "最大值结果: " + max);
    }

    private void calculateMin() {
        double min = Double.MAX_VALUE;

        // 遍历单元格的值，并找到最小值
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                String value = cells[i][j].getText();
                if (!value.isEmpty()) {
                    double cellValue = Double.parseDouble(value);
                    if (cellValue < min) {
                        min = cellValue;
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this, "最小值结果: " + min);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int rows = Integer.parseInt(JOptionPane.showInputDialog("请输入行数："));
                int cols = Integer.parseInt(JOptionPane.showInputDialog("请输入列数："));
                new SpreadsheetGUI(rows, cols); // 创建指定行数和列数的电子表格
            }
        });
    }
}
