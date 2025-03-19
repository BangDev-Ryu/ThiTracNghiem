package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HomeGUI extends JPanel {

    private int width, height;
    private JPanel pnTable, pnDetail, pnGraph;
    private ArrayList<JPanel> columnList;
    
    private JTable table;
    private DefaultTableModel tableModel;

    public HomeGUI(int width, int height) {
        this.width = width;
        this.height = height;
        this.columnList = new ArrayList<>();
        init();
    }

    public void init() {
        this.setSize(this.width, this.height);

        this.pnDetail = initPanelDetail();
        this.pnGraph = initPanelGraph();
        this.pnTable = initPanelTable();

        this.setLayout(new BorderLayout());

        this.add(pnDetail, BorderLayout.NORTH);
        this.add(pnGraph, BorderLayout.CENTER);
        this.add(pnTable, BorderLayout.SOUTH);
    }

    public JPanel initPanelDetail() {
        JPanel pn = new JPanel();
        pn.setPreferredSize(new Dimension(this.width, 100));
        pn.setLayout(new FlowLayout(1, 0, 0));

        // Create child panels for displaying details
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel1.setBackground(Color.decode("#3498db"));
        panel1.add(new JLabel("SỐ THÍ SINH"));

        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel2.setBackground(Color.decode("#3498db"));
        panel2.add(new JLabel("SỐ ĐỀ THI"));

        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel3.setBackground(Color.decode("#3498db"));
        panel3.add(new JLabel("SỐ LƯỢT LÀM BÀI"));

        JPanel panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel4.setBackground(Color.decode("#3498db"));
        panel4.add(new JLabel("TOP 3"));
        
        
        panel1.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        panel2.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        panel3.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        panel4.setBorder(BorderFactory.createLineBorder(Color.white, 5));

        pn.add(panel1);
        pn.add(panel2);
        pn.add(panel3);
        pn.add(panel4);

        return pn;
    }

    public JPanel initPanelGraph() {
        JPanel pn_table = new JPanel() {
            // Override the paintComponent method for custom drawing
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Make sure to call this to handle normal painting
                drawPieChart(g); // Call the method to draw the pie chart
            }
        };
        pn_table.setLayout(null); // Use null layout for custom positioning
        pn_table.setPreferredSize(new Dimension(this.width, 200));

        addLegend(pn_table);
        addYAxis(pn_table);
        createBars(pn_table);

        JLabel labelYear = new JLabel("2025");
        labelYear.setFont(new Font("Arial", Font.BOLD, 16));
        labelYear.setBounds(350, 250, 40, 20); // Tính toán vị trí chính giữa
        pn_table.add(labelYear);

        return pn_table;
    }

    private void drawPieChart(Graphics g) {
        // Vẽ biểu đồ tròn (27% không đạt, 73% đạt)
        int x = 800, y = 60, diameter = 180; // Vị trí và kích thước của biểu đồ tròn

        // Vẽ phần đạt (73%)
        g.setColor(Color.GREEN);
        g.fillArc(x, y, diameter, diameter, 0, (int)(73 * 3.6)); // Vẽ phần đạt

        // Vẽ phần không đạt (27%)
        g.setColor(Color.RED);
        g.fillArc(x, y, diameter, diameter, (int)(73 * 3.6), (int)(27 * 3.6)); // Vẽ phần không đạt

        // Vẽ vòng tròn viền (bằng màu đen)
        g.setColor(Color.BLACK);
        g.drawArc(x, y, diameter, diameter, 0, 360);
    }

    // Tạo các cột biểu đồ và nhãn tháng
    private void createBars(JPanel pn_table) {
        int[] thitSinhData = {4, 6, 3, 5, 5, 8, 5, 8, 9, 5, 8, 5};
        int[] thiSinhDatData = {4, 6, 3, 8, 2, 7, 5, 1, 9, 8, 6, 5};

        // Mảng chứa tên các tháng
        String[] months = {"SL","Th1", "Th2", "Th3", "Th4", "Th5", "Th6", "Th7", "Th8", "Th9", "Th10", "Th11", "Th12", "Điểm"};

        for (int i = 0; i < 14; i++) {
            // Tạo cột cho "Thí sinh đã thi"
            JPanel column1 = new JPanel();
            column1.setBackground(Color.BLUE);
            column1.setBounds(60 + (i * 50), 220, 20, 0); // Điều chỉnh vị trí cột
            columnList.add(column1);
            pn_table.add(column1);

            // Tạo cột cho "Thí sinh đạt"
            JPanel column2 = new JPanel();
            column2.setBackground(Color.GREEN);
            column2.setBounds(80 + (i * 50), 220, 20, 0); // Điều chỉnh vị trí cột
            columnList.add(column2);
            pn_table.add(column2);

            // Thêm nhãn tháng dưới giữa hai cột
            JLabel monthLabel = new JLabel(months[i]);
            int labelX = 20 + (i * 50); // Vị trí x nằm giữa hai cột
            monthLabel.setBounds(labelX, 230, 40, 20); // Đặt nhãn dưới hai cột
            monthLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            pn_table.add(monthLabel);
        }

        // Bắt đầu hoạt ảnh cột
        animateBars(thitSinhData, thiSinhDatData);
    }

    // Thêm chú thích cho biểu đồ
    private void addLegend(JPanel pn_table) {
        // Chú thích cho "Thí sinh đã thi"
        JPanel legend1 = new JPanel();
        legend1.setBackground(Color.BLUE);
        legend1.setBounds(400, 10, 20, 20); // Vị trí của chú thích
        pn_table.add(legend1);

        JLabel label1 = new JLabel("Thí sinh đã thi");
        label1.setBounds(425, 10, 100, 20); // Vị trí văn bản chú thích
        label1.setFont(new Font("Arial", Font.PLAIN, 12));
        pn_table.add(label1);

        // Chú thích cho "Thí sinh đạt"
        JPanel legend2 = new JPanel();
        legend2.setBackground(Color.GREEN);
        legend2.setBounds(550, 10, 20, 20); // Vị trí của chú thích
        pn_table.add(legend2);

        JLabel label2 = new JLabel("Điểm trung bình");
        label2.setBounds(575, 10, 100, 20); // Vị trí văn bản chú thích
        label2.setFont(new Font("Arial", Font.PLAIN, 12));
        pn_table.add(label2);
    }

    private void addYAxis(JPanel pn_table) {
        int labelY = 0;
        // Thêm nhãn cho trục Y bên trái (Số thí sinh)
        for (int i = 0; i <= 5; i++) { // Điều chỉnh chỉ số cho trục Y bên trái (từ 0 đến 60)
            JLabel label = new JLabel(String.valueOf(i * 10)); // Giá trị từ 0 đến 60
            labelY = 270 - (i * 30); // Tính toán vị trí cho nhãn
            label.setBounds(20, labelY - 60, 40, 20); // Điều chỉnh nhãn nằm giữa các vạch
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            pn_table.add(label);
            
        }

        // Thêm nhãn cho trục Y bên phải (Điểm)
        for (int i = 0; i <= 5; i++) { // Điều chỉnh chỉ số cho trục Y bên phải (từ 0 đến 10)
            JLabel label = new JLabel(String.valueOf(i * 2)); // Giá trị từ 0 đến 10
            labelY = 270 - (i * 30); // Tính toán vị trí cho nhãn
            label.setBounds(675, labelY - 60, 40, 20); // Điều chỉnh nhãn nằm giữa các vạch
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            pn_table.add(label);
        }

        // Thêm các đường trục Y
        for (int i = 0; i <= 5; i++) {
            JPanel line = new JPanel();
            line.setBounds(45, 220 - (i * 30), 620, 1); // Vị trí các đường vạch trục Y
            line.setBackground(new Color(0, 0, 0, 90)); // Màu sắc mờ cho các vạch trục
            pn_table.add(line);
        }

        // Thêm đường trục Y dưới cùng
        JPanel bottomLine = new JPanel();
        bottomLine.setBounds(45, 220, 620, 1);
        bottomLine.setBackground(Color.BLACK);
        pn_table.add(bottomLine);
    }

    private void animateBars(int[] thitSinhData, int[] thiSinhDatData) {
        for (int i = 0; i < 12; i++) {
            final int index = i;

            // Animate Thí sinh đã thi
            new Thread(() -> {
                JPanel column = columnList.get(index * 2); // "Thí sinh đã thi" column
                int targetHeight = thitSinhData[index] * 20; // The height to animate to
                animateColumnHeight(column, targetHeight);
            }).start();

            // Animate Thí sinh đạt
            new Thread(() -> {
                JPanel column = columnList.get(index * 2 + 1); // "Thí sinh đạt" column
                int targetHeight = thiSinhDatData[index] * 20; // The height to animate to
                animateColumnHeight(column, targetHeight);
            }).start();
        }
    }

    private void animateColumnHeight(JPanel column, int targetHeight) {
        int currentHeight = column.getHeight();

        for (int i = currentHeight; i <= targetHeight; i++) {
            final int finalI = i;
            javax.swing.SwingUtilities.invokeLater(() -> {
                column.setBounds(column.getX(), 220 - finalI, column.getWidth(), finalI);
                column.repaint(); // Repaint to show the change
            });

            try {
                Thread.sleep(5); // Slow down the animation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public JPanel initPanelTable() {
        JPanel pn_table = new JPanel(new FlowLayout(1, 0, 0));
        pn_table.setPreferredSize(new Dimension(this.width, 250));
        
        tableModel = new DefaultTableModel(new Object[]{
            "STT", "Đề thi", "Dự thi", "Đạt", "Không đạt", "Thời gian", "Điểm TB"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));
        
        pn_table.add(scrollPane);

        return pn_table;
    }
}
