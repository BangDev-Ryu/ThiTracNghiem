package GUI;

import BLL.ExamBLL;
import BLL.ResultBLL;
import BLL.UserBLL;
import DTO.ExamDTO;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HomeGUI extends JPanel {

    private int width, height;
    private JPanel pnTable, pnDetail, pnGraph;
    private ArrayList<JPanel> columnList;
    private UserBLL userBLL = new UserBLL();
    private ResultBLL resultBLL = new ResultBLL();
    
    private JTable table;
    private DefaultTableModel tableModel;
    
    double percentAchieved = 100;
    double percentNotAchieved = 0;

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
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panel1.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel1.setBackground(Color.decode("#3498db"));
        JLabel label1 = new JLabel("SỐ THÍ SINH");
        label1.setFont(new Font("Arial", Font.PLAIN, 25));
        label1.setForeground(Color.white);
        String totalUsers = String.valueOf(userBLL.getAllUsersNotIsAdmin());
        JLabel label12 = new JLabel(totalUsers);
        label12.setFont(new Font("Arial", Font.PLAIN, 25));
        label12.setForeground(Color.white);
        panel1.add(label1);
        panel1.add(label12);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panel2.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel2.setBackground(Color.ORANGE);
        JLabel label2 = new JLabel("SỐ LƯỢT THI");
        label2.setFont(new Font("Arial", Font.PLAIN, 25));
        label2.setForeground(Color.white);
        String totalResult = String.valueOf(resultBLL.getTotalResult());
        JLabel label22 = new JLabel(totalResult);
        label22.setFont(new Font("Arial", Font.PLAIN, 25));
        label22.setForeground(Color.white);
        panel2.add(label2);
        panel2.add(label22);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panel3.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel3.setBackground(Color.GREEN);
        JLabel label3 = new JLabel("SỐ BÀI ĐẠT");
        label3.setFont(new Font("Arial", Font.PLAIN, 25));
        label3.setForeground(Color.white);
        String totalAbove50 = String.valueOf(resultBLL.getTotalResultAbove50());
        JLabel label33 = new JLabel(totalAbove50);
        label33.setFont(new Font("Arial", Font.PLAIN, 25));
        label33.setForeground(Color.white);
        panel3.add(label3);
        panel3.add(label33);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panel4.setPreferredSize(new Dimension(this.width / 4 - 5, 100));
        panel4.setBackground(Color.RED);
        JLabel label4 = new JLabel("SỐ BÀI KHÔNG ĐẠT");
        label4.setFont(new Font("Arial", Font.PLAIN, 25));
        label4.setForeground(Color.white);
        String totalBelow50 = String.valueOf(resultBLL.getTotalResultBelow50());
        JLabel label44 = new JLabel(totalBelow50);
        label44.setFont(new Font("Arial", Font.PLAIN, 25));
        label44.setForeground(Color.white);
        panel4.add(label4);
        panel4.add(label44);
        
        
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
        addLegendPie(pn_table);
        addYAxis(pn_table);
        createBars(pn_table);

        JLabel labelYear = new JLabel("Thống Kê Bài Thi                                    "
                + " 2025                                                                                             "
                + "Tỷ Lệ Kết Quả Bài Thi");
        labelYear.setFont(new Font("Arial", Font.BOLD, 16));
        labelYear.setBounds(20, 265, 1200, 20); // Tính toán vị trí chính giữa
        pn_table.add(labelYear);

        return pn_table;
    }

    private void drawPieChart(Graphics g) {
        int x = 800, y = 60, diameter = 180; // Vị trí và kích thước của biểu đồ tròn

        // Vẽ phần đạt (tỷ lệ đạt)
        g.setColor(Color.GREEN);
        g.fillArc(x, y, diameter, diameter, 0, (int)(percentAchieved * 3.6)); // Vẽ phần đạt

        // Vẽ phần không đạt (tỷ lệ không đạt)
        g.setColor(Color.RED);
        g.fillArc(x, y, diameter, diameter, (int)(percentAchieved * 3.6), (int)(percentNotAchieved * 3.6)); // Vẽ phần không đạt

        // Vẽ vòng tròn viền (bằng màu đen)
        g.setColor(Color.BLACK);
        g.drawArc(x, y, diameter, diameter, 0, 360);
    }


    // Tạo các cột biểu đồ và nhãn tháng
    private void createBars(JPanel pn_table) {

        // Mảng chứa tên các tháng
        String[] months = {"SL","Th1", "Th2", "Th3", "Th4", "Th5", "Th6", "Th7", "Th8", "Th9", "Th10", "Th11", "Th12", "Điểm"};

        int[] thitSinhData = new int[12];  // Tổng số lượng thi sinh mỗi tháng
        double[] thiSinhDatData = new double[12];  // Điểm trung bình mỗi tháng

        // Lấy dữ liệu từ ResultBLL
        for (int i = 0; i < 12; i++) {
            // Lấy tổng số bài thi của tháng i
            thitSinhData[i] = resultBLL.getTotalResultsByMonth(i + 1);

            // Lấy điểm trung bình của các bài thi trong tháng i
            thiSinhDatData[i] = resultBLL.getAverageMarkByMonth(i + 1);
        }
        
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

   private void addLegendPie(JPanel pn_table) {
        // Xóa các nhãn hiện tại (nếu có) để tránh việc vẽ chồng lên nhau
        for (java.awt.Component comp : pn_table.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().endsWith("%")) {
                pn_table.remove(comp);  // Xóa các nhãn có chứa "%" (nhãn phần trăm cũ)
            }
            if (comp instanceof JLabel && ((JLabel) comp).getText().endsWith("ạt")) {
                pn_table.remove(comp);  // Xóa các nhãn có chứa "%" (nhãn phần trăm cũ)
            }
        }

        // Chú thích cho "Thí sinh đạt"
        JLabel label0 = new JLabel(String.format("%.2f%%", percentAchieved)); // Hiển thị phần trăm đạt với 2 chữ số thập phân
        label0.setBounds(750, 10, 40, 20);
        pn_table.add(label0);

        JPanel legend1 = new JPanel();
        legend1.setBackground(Color.green);
        legend1.setBounds(800, 10, 20, 20); // Vị trí của chú thích
        pn_table.add(legend1);

        JLabel label1 = new JLabel("Đạt");
        label1.setBounds(825, 10, 100, 20); // Vị trí văn bản chú thích
        label1.setFont(new Font("Arial", Font.PLAIN, 12));
        pn_table.add(label1);

        // Chú thích cho "Thí sinh không đạt"
        JLabel label3 = new JLabel(String.format("%.2f%%", percentNotAchieved)); // Hiển thị phần trăm không đạt với 2 chữ số thập phân
        label3.setBounds(860, 10, 40, 20);
        pn_table.add(label3);

        JPanel legend2 = new JPanel();
        legend2.setBackground(Color.red);
        legend2.setBounds(900, 10, 20, 20); // Vị trí của chú thích
        pn_table.add(legend2);

        JLabel label2 = new JLabel("Không đạt");
        label2.setBounds(925, 10, 100, 20); // Vị trí văn bản chú thích
        label2.setFont(new Font("Arial", Font.PLAIN, 12));
        pn_table.add(label2);

        // Cập nhật lại giao diện (repaint)
        pn_table.repaint();
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
            JLabel label = new JLabel(String.valueOf(i * 20)); // Giá trị từ 0 đến 10
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

    private void animateBars(int[] thitSinhData, double[] thiSinhDatData) {
        for (int i = 0; i < 12; i++) {
            final int index = i;

            // Animate Thí sinh đã thi
            new Thread(() -> {
                JPanel column = columnList.get(index * 2); // "Thí sinh đã thi" column
                int targetHeight = thitSinhData[index] * 2; // The height to animate to
                animateColumnHeight(column, targetHeight);
            }).start();

            // Animate Thí sinh đạt
            new Thread(() -> {
                JPanel column = columnList.get(index * 2 + 1); // "Thí sinh đạt" column
                double targetHeight = thiSinhDatData[index] * 1.5; // The height to animate to
                System.out.println(targetHeight);
                animateColumnHeight(column, targetHeight);
            }).start();
        }
    }

    private void animateColumnHeight(JPanel column, double targetHeight) {
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

        // Tạo mô hình bảng với các tiêu đề cột
        tableModel = new DefaultTableModel(new Object[]{
            "STT", "Bài thi", "Lượt thi", "Đạt", "Không đạt", "Điểm TB"
        }, 0);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));

        // Lấy danh sách các bài thi từ BLL
        ExamBLL examBLL = new ExamBLL();
        ArrayList<ExamDTO> exams = examBLL.getAllExams();

        // Duyệt qua danh sách các bài thi và thêm vào bảng
        int stt = 1;
        for (ExamDTO exam : exams) {
            tableModel.addRow(new Object[]{
                stt++,                          // STT
                exam.getTestCode(),             // Bài thi (test_code)
                resultBLL.getTotalResultsByTestId(exam.getId()),                              // Dự thi (Bạn có thể cập nhật thêm dữ liệu nếu có)
                resultBLL.getTotalResultsAbove50ByTestId(exam.getId()),                              // Đạt
                resultBLL.getTotalResultsBelow50ByTestId(exam.getId()),  
                resultBLL.getMarkTBTestId(exam.getId())                               // Điểm TB
            });
        }

        // Căn giữa dữ liệu trong các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Căn giữa cho tất cả các cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Đoạn mã trong phương thức initPanelTable (sự kiện nhấp chuột)
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow(); // Lấy chỉ số dòng được chọn
                if (row >= 0) {
                    // Cách kiểm tra và chuyển đổi kiểu an toàn
                    try {
                        int totalResults = Integer.parseInt(table.getValueAt(row, 2).toString()); // Cột "Lượt thi"
                        int totalAbove50 = Integer.parseInt(table.getValueAt(row, 3).toString()); // Cột "Đạt"

                        System.out.println(totalAbove50);

                        if (totalResults > 0) {
                            // Tính phần trăm đạt
                            percentAchieved = (double) totalAbove50 / totalResults * 100;
                            percentNotAchieved = (double) 100 - percentAchieved;
                            System.out.println(percentAchieved);

                            // Cập nhật chú thích phần trăm
                            addLegendPie(pnGraph); // Cập nhật phần trăm vào chú thích
                            pnGraph.repaint(); // Vẽ lại biểu đồ
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi khi chuyển đổi dữ liệu từ bảng: " + e.getMessage());
                    }
                }
            }
        });




        pn_table.add(scrollPane);

        return pn_table;
    }

}
