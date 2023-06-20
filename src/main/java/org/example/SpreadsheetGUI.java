import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpreadsheetGUI extends JFrame {
    private JTextField[][] cells;
    private JMenuItem sumMenuItem;
    private JMenuItem averageMenuItem;
    private JMenuItem maxMenuItem;
    private JMenuItem minMenuItem;
    private JMenuItem createMenuItem;
    private int selectedStartRow = -1;
    private int selectedStartCol = -1;
    private int selectedEndRow = -1;
    private int selectedEndCol = -1;

    public SpreadsheetGUI(int rows, int cols) {
        setTitle("电子表格");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cells = new JTextField[rows][cols];
        JPanel panel = new JPanel(new GridLayout(rows, cols));

        // 创建单元格文本框并设置固定大小为5x10
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setPreferredSize(new Dimension(100, 50)); // 设置固定大小为5x10
                panel.add(cells[i][j]);
            }
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu functionMenu = new JMenu("功能");

        // 创建菜单项并添加到菜单中
        sumMenuItem = new JMenuItem("合计");
        averageMenuItem = new JMenuItem("平均");
        maxMenuItem = new JMenuItem("最大");
        minMenuItem = new JMenuItem("最小");
        createMenuItem = new JMenuItem("创建新表格");

        functionMenu.add(sumMenuItem);
        functionMenu.add(averageMenuItem);
        functionMenu.add(maxMenuItem);
        functionMenu.add(minMenuItem);
        functionMenu.add(createMenuItem);

        menuBar.add(functionMenu);
        setJMenuBar(menuBar);

        // 添加菜单项的点击事件监听器
        sumMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取起始和结束的行和列
                getStartAndEndIndices();
                calculateSum();
            }
        });

        averageMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取起始和结束的行和列
                getStartAndEndIndices();
                calculateAverage();
            }
        });

        maxMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取起始和结束的行和列
                getStartAndEndIndices();
                calculateMax();
            }
        });

        minMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取起始和结束的行和列
                getStartAndEndIndices();
                calculateMin();
            }
        });

        createMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows = Integer.parseInt(JOptionPane.showInputDialog("请输入行数："));
                int cols = Integer.parseInt(JOptionPane.showInputDialog("请输入列数："));
                createNewSpreadsheet(rows, cols);
            }
        });

        // 添加鼠标点击事件监听器
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final int row = i;
                final int col = j;
                cells[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        //点击时间
                    }
                });
            }
        }

        add(panel);
        pack();

        setVisible(true);
    }


    private void getStartAndEndIndices() {
        int startRow = Integer.parseInt(JOptionPane.showInputDialog("请输入起始行："));
        int startCol = Integer.parseInt(JOptionPane.showInputDialog("请输入起始列："));
        int endRow = Integer.parseInt(JOptionPane.showInputDialog("请输入结束行："));
        int endCol = Integer.parseInt(JOptionPane.showInputDialog("请输入结束列："));

        // 设置选取的起始和结束行列
        selectedStartRow = startRow;
        selectedStartCol = startCol;
        selectedEndRow = endRow;
        selectedEndCol = endCol;
    }

    private double[][] getSelectedData() {
        if (selectedStartRow == -1 || selectedEndRow == -1 || selectedStartCol == -1 || selectedEndCol == -1) {
            // 如果没有选取起始和结束单元格，则返回空数据
            return new double[0][0];
        }

        // 获取选取的起始行、结束行、起始列、结束列
        int startRow = Math.min(selectedStartRow, selectedEndRow);
        int endRow = Math.max(selectedStartRow, selectedEndRow);
        int startCol = Math.min(selectedStartCol, selectedEndCol);
        int endCol = Math.max(selectedStartCol, selectedEndCol);

        // 计算选取的行数和列数
        int numRows = endRow - startRow + 1;
        int numCols = endCol - startCol + 1;

        double[][] selectedData = new double[numRows][numCols];

        // 将选取的数据转换为double类型存储到selectedData数组中
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                String value = cells[i][j].getText();
                if (!value.isEmpty()) {
                    selectedData[i - startRow][j - startCol] = Double.parseDouble(value);
                }
            }
        }

        // 清除选取的起始和结束信息
        selectedStartRow = -1;
        selectedStartCol = -1;
        selectedEndRow = -1;
        selectedEndCol = -1;

        return selectedData;
    }

    private void calculateSum() {
        double[][] selectedData = getSelectedData();
        double sum = 0;

        // 遍历选取的数据，并计算总和
        for (int i = 0; i < selectedData.length; i++) {
            for (int j = 0; j < selectedData[0].length; j++) {
                sum += selectedData[i][j];
            }
        }

        JOptionPane.showMessageDialog(this, "合计结果: " + sum);
    }

    private void calculateAverage() {
        double[][] selectedData = getSelectedData();
        double sum = 0;
        int count = 0;

        // 遍历选取的数据，并计算总和和数量
        for (int i = 0; i < selectedData.length; i++) {
            for (int j = 0; j < selectedData[0].length; j++) {
                sum += selectedData[i][j];
                count++;
            }
        }

        // 计算平均值
        double average = sum / count;

        JOptionPane.showMessageDialog(this, "平均结果: " + average);
    }

    private void calculateMax() {
        double[][] selectedData = getSelectedData();
        double max = Double.MIN_VALUE;

        // 遍历选取的数据，并找到最大值
        for (int i = 0; i < selectedData.length; i++) {
            for (int j = 0; j < selectedData[0].length; j++) {
                if (selectedData[i][j] > max) {
                    max = selectedData[i][j];
                }
            }
        }

        JOptionPane.showMessageDialog(this, "最大结果: " + max);
    }

    private void calculateMin() {
        double[][] selectedData = getSelectedData();
        double min = Double.MAX_VALUE;

        // 遍历选取的数据，并找到最小值
        for (int i = 0; i < selectedData.length; i++) {
            for (int j = 0; j < selectedData[0].length; j++) {
                if (selectedData[i][j] < min) {
                    min = selectedData[i][j];
                }
            }
        }

        JOptionPane.showMessageDialog(this, "最小结果: " + min);
    }

    void createNewSpreadsheet(int rows, int cols) {
        getContentPane().removeAll();
        getContentPane().revalidate();
        getContentPane().repaint();
        new SpreadsheetGUI(rows, cols);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int rows = Integer.parseInt(JOptionPane.showInputDialog("请输入行数："));
                int cols = Integer.parseInt(JOptionPane.showInputDialog("请输入列数："));
                new SpreadsheetGUI(rows, cols);
            }
        });
    }
}
