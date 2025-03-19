package GUI;



import BLL.TopicBLL;
import DTO.TopicDTO;
import GUI.component.TopicDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TopicGUI extends JPanel {
    private TopicBLL topicBLL = new TopicBLL();
    private int width, height;
    
    private ArrayList<JPanel> arrPnInfor;
    private ArrayList<JLabel> arrLbInfor;
    private ArrayList<JTextField> arrTfInfor;
    
    private JPanel pnTable, pnInfo, pnFilter;
    private JButton btnThem, btnSua, btnXoa;
    private JTable table, table2;
    private DefaultTableModel tableModel, tableMode2;
    private TableRowSorter<TableModel> rowSorter;

    public TopicGUI(int width, int height) {
        this.width = width;
        this.height = height;
        init();
    }

    public void init() {
        this.setSize(this.width, this.height);

        this.pnInfo = createPnInfor();
        this.pnTable = initPanelTable();
        this.pnFilter = this.createPnFilter();
        
        this.setLayout(new BorderLayout());
        this.add(pnInfo, BorderLayout.NORTH); 
        this.add(pnFilter, BorderLayout.CENTER);
        this.add(pnTable, BorderLayout.SOUTH); 
    }

    //Thông tin và thao tác
    public JPanel createPnInfor() {
        JPanel result = new JPanel(new FlowLayout(1, 0, 25));
        result.setPreferredSize(new Dimension(this.width, 250));
        
        JPanel pn_infor = new JPanel(new BorderLayout());
        pn_infor.setPreferredSize(new Dimension(this.width - 50, 200));
        
        JPanel pn_desc = new JPanel(new FlowLayout(1, 20, 20));
        pn_desc.setPreferredSize(new Dimension(300, 200));
        String[] thuoc_tinh = {"ID", "Name", "Parent"};
        int len = thuoc_tinh.length;
        this.arrPnInfor = new ArrayList<>();
        this.arrLbInfor = new ArrayList<>();
        this.arrTfInfor = new ArrayList<>();
        Dimension d_pn = new Dimension(250, 30);
        Dimension d_lb = new Dimension(50, 30);
        Dimension d_tf = new Dimension(200, 30);
        for (int i = 0; i < len; i++) {
            this.arrPnInfor.add(new JPanel(new FlowLayout(0, 0, 0)));
            this.arrPnInfor.get(i).setPreferredSize(d_pn);
            
            this.arrLbInfor.add(new JLabel(thuoc_tinh[i]));
            this.arrLbInfor.get(i).setPreferredSize(d_lb);
            this.arrTfInfor.add(new JTextField());
            this.arrTfInfor.get(i).setPreferredSize(d_tf);
            
            this.arrPnInfor.get(i).add(this.arrLbInfor.get(i));
            this.arrPnInfor.get(i).add(this.arrTfInfor.get(i));
            pn_desc.add(this.arrPnInfor.get(i));
            this.arrTfInfor.get(i).setEditable(false);
        }
        
        //Bảng con
        tableMode2 = new DefaultTableModel(new Object[] {
            "ID", "Name", "Parent"
        }, 0);
        table2 = new JTable(tableMode2);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table2.getColumnCount(); i++) {
                table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setPreferredSize(new Dimension(450, 200));
        
        // Button
        JPanel pn_btn = new JPanel(new FlowLayout(1, 30, 20));
        pn_btn.setPreferredSize(new Dimension(220, 200));
        btnThem = new JButton("Thêm chủ đề");
        btnSua = new JButton("Sửa chủ đề");
        btnXoa = new JButton("Xóa chủ đề");
        Dimension d_btn = new Dimension(150, 30);
        btnThem.setPreferredSize(d_btn);
        btnSua.setPreferredSize(d_btn);
        btnXoa.setPreferredSize(d_btn);
        pn_btn.add(btnThem);
        pn_btn.add(btnSua);
        pn_btn.add(btnXoa);
        
        this.btnThem.addActionListener(e -> addTopic());
        this.btnSua.addActionListener(e -> updateTopic());
        
        btnXoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (arrTfInfor.get(0).getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn chủ đề cần xóa!");
                    return;
                }
                
                int confirmed = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
                if (confirmed == 0) { // xác nhận xóa
                    int id = Integer.parseInt(arrTfInfor.get(0).getText());
                    topicBLL.deleteTopic(id);
                    table.clearSelection();
                    reloadTopic(topicBLL.getAllTopics());
                }
            }
        });

        pn_infor.add(pn_desc, BorderLayout.WEST);
        pn_infor.add(pn_btn, BorderLayout.EAST);
        pn_infor.add(scrollPane2, BorderLayout.CENTER);
        result.add(pn_infor);
        
        return result;
    }

    //Tìm kiếm
    public JPanel createPnFilter() {
        JPanel pn_filter = new JPanel(new FlowLayout(1, 20, 20));
        JLabel lb_tim_kiem = new JLabel("Tìm kiếm");
        JPanel pn_tim_kiem = new JPanel(new FlowLayout(1, 0, 0));
        pn_tim_kiem.setPreferredSize(new Dimension(350, 30));
        JTextField tf_tim_kiem = new JTextField();
        tf_tim_kiem.setPreferredSize(new Dimension(350, 30));
        tf_tim_kiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = tf_tim_kiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                }
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text + "", 1, 2)); 
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = tf_tim_kiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                }
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text + "", 1, 2)); 
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            } 
        });
        
//        pn_tim_kiem.add(cb_tim_kiem);
        pn_tim_kiem.add(tf_tim_kiem);
        
        pn_filter.add(lb_tim_kiem);
        pn_filter.add(pn_tim_kiem);
        
        return pn_filter;
    }
    
    //Danh sách chủ đề
    public JPanel initPanelTable() {
        JPanel pn_table = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pn_table.setPreferredSize(new Dimension(this.width - 200, 350));
        tableModel = new DefaultTableModel(new Object[] {
            "ID", "Name", "Parent"
        }, 0);
        table = new JTable(tableModel);
        
        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Kiểm tra nếu có dòng được chọn
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy thông tin của dòng được chọn
                    String id = table.getValueAt(selectedRow, 0).toString();
                    String name = table.getValueAt(selectedRow, 1).toString();
                    String parent = table.getValueAt(selectedRow, 2).toString();

                    // Hiển thị thông tin vào các JTextField trong pn_desc
                    arrTfInfor.get(0).setText(id);
                    arrTfInfor.get(1).setText(name);
                    arrTfInfor.get(2).setText(parent);
                    
                    loadChildTopics(id);
                }
            }
        });
        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setPreferredSize(new Dimension(this.width - 100, 330));
        
        this.loadTopics();
        pn_table.add(scrollPane1);
        

        return pn_table;
    }
    
    //Lấy dữ liệu danh sách
    public void loadTopics() {
        ArrayList<TopicDTO> topics = topicBLL.getAllTopics();
        tableModel.setRowCount(0);
        reloadTopic(topics);
    }
    
    public void reloadTopic(ArrayList<TopicDTO> topics) {
        tableModel.setRowCount(0);
        for (TopicDTO t : topics) {
            tableModel.addRow(new Object[]{
                t.getId(),
                t.getName(),
                t.getParent()
            });
        }
    }
    
    public void loadChildTopics(String parentId) {
        // Xóa tất cả dữ liệu trong tableMode2
        tableMode2.setRowCount(0);

        // Lấy danh sách các chủ đề
        ArrayList<TopicDTO> topics = topicBLL.getAllTopics();

        // Lọc các chủ đề có parentId bằng với id của chủ đề đã chọn
        for (TopicDTO t : topics) {
            if (String.valueOf(t.getParent()).equals(parentId)) {
                // Thêm các chủ đề con vào tableMode2
                tableMode2.addRow(new Object[] {
                    t.getId(),
                    t.getName(),
                    t.getParent()
                });
            }
        }
    }

    public void addTopic() {
        TopicDialog topicDialog = new TopicDialog("Thêm chủ đề", -1);
        reloadTopic(topicBLL.getAllTopics());
    }
    
    public void updateTopic() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) table.getValueAt(selectedRow, 0);
            TopicDialog topicDialog = new TopicDialog("Sửa chủ đề", id);
            reloadTopic(topicBLL.getAllTopics());
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn chủ đề cần sửa");
            return;
        }
        
    }
}
