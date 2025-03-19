package GUI;

import BLL.UserBLL;
import DTO.UserDTO;
import GUI.component.UserDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class UserGUI extends JPanel {
    private UserBLL userBLL = new UserBLL();
    
    private int width, height;
    private JTable tableUser;
    private DefaultTableModel tableModelUser;
    
    private JPanel pnUser, pnFilter;
    private JTextField tfSearch;
    private JButton btnSearch;
    private JButton btnAddUser, btnUpdateUser, btnDelUser;
    
    public UserGUI(int width, int height) {
        this.width = width;
        this.height = height;
        init();
    }
    
    public void init() {
        this.setSize(this.width, this.height);
        
        this.pnFilter = initPanelFilter();
        this.pnUser = initPanelUser();
        
        this.setLayout(new BorderLayout());
        
        this.add(pnFilter, BorderLayout.CENTER);
        this.add(pnUser, BorderLayout.SOUTH);
    }
    
    public JPanel initPanelFilter(){
        JPanel pn = new JPanel();
        pn.setPreferredSize(new Dimension(this.width, 100));
        pn.setLayout(new FlowLayout(1, 0, 0));
        
        Dimension btn_size = new Dimension(120, 30);
        Font btn_font = new Font("SansSerif", Font.BOLD, 12);
        
        JPanel pn_filter = new JPanel(new FlowLayout(1, 10, 50));
        pn_filter.setPreferredSize(new Dimension(this.width/2 + 80, 100));
        
        this.tfSearch = new JTextField();
        this.tfSearch.setPreferredSize(new Dimension(300, 30));
        
        this.btnSearch = new JButton("Tìm kiếm");
        this.btnSearch.setPreferredSize(new Dimension(100, 30));
        this.btnSearch.setFont(btn_font);
        
        pn_filter.add(tfSearch);
        pn_filter.add(btnSearch);
        
        JPanel pn_btn = new JPanel(new FlowLayout(0, 20, 50));
        pn_btn.setPreferredSize(new Dimension(this.width/2 - 80, 100));
        
        this.btnAddUser = new JButton("Thêm tài khoản");
        this.btnUpdateUser = new JButton("Sửa tài khoản");
        this.btnDelUser = new JButton("Xóa tài khoản");
        
        this.btnAddUser.setPreferredSize(btn_size);
        this.btnUpdateUser.setPreferredSize(btn_size);
        this.btnDelUser.setPreferredSize(btn_size);
        
        this.btnAddUser.setFont(btn_font);
        this.btnUpdateUser.setFont(btn_font);
        this.btnDelUser.setFont(btn_font);
        
        pn_btn.add(btnAddUser);
        pn_btn.add(btnUpdateUser);
        pn_btn.add(btnDelUser);
        
        this.btnAddUser.addActionListener(e -> addUser());
        this.btnUpdateUser.addActionListener(e -> updateUser());
        this.btnDelUser.addActionListener(e -> deleteUser());
        this.btnSearch.addActionListener(e -> searchUsers());
        
        pn.add(pn_filter);
        pn.add(pn_btn);
        
        return pn;
    }
    
    public JPanel initPanelUser() {
        JPanel pn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pn.setPreferredSize(new Dimension(this.width, 320));
        
        tableModelUser = new DefaultTableModel(new Object[]{
            "ID", "Tên", "Email", "Mật khẩu", "Họ tên", "Chức vụ"
        }, 0);
        tableUser = new JTable(tableModelUser);
        tableUser.setDefaultEditor(Object.class, null);
        tableUser.getTableHeader().setReorderingAllowed(false);
        tableUser.getTableHeader().setResizingAllowed(false);

        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableUser.getColumnCount(); i++) {
            tableUser.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        TableColumnModel columnModel = tableUser.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(200);
        columnModel.getColumn(5).setPreferredWidth(100);
        
        tableUser.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(tableUser);
        scrollPane.setPreferredSize(new Dimension(1000, 250));
        
        pn.add(scrollPane);
        
        loadUsers();
        
        return pn;
    }
    
    public void loadUsers() {
        this.tableModelUser.setRowCount(0);
        ArrayList<UserDTO> users = userBLL.getAllUsers();
        for (UserDTO q : users) {
            String chucvu = q.isAdmin() ? "Admin" : "Người dùng";
            tableModelUser.addRow(new Object[] {
                q.getId(),
                q.getName(),
                q.getEmail(),
                q.getPassword(),
                q.getFullname(),
                chucvu
            });
        }
    }
    
    public void addUser() {
        new UserDialog("Thêm tài khoản", -1);
        loadUsers();
    }
    
    public void updateUser() {
        int selectedRow = tableUser.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModelUser.getValueAt(selectedRow, 0);
            new UserDialog("Sửa tài khoản", id);
            loadUsers();
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản cần sửa");
            return;
        }
    }
    
    public void deleteUser() {
        int selectedRow = tableUser.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModelUser.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                userBLL.deleteUser(id);
                loadUsers();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản cần xóa");
            return;
        }
    }
    
    public void searchUsers() {
        String keyword = tfSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadUsers(); // Nếu ô tìm kiếm trống, hiển thị tất cả user
            return;
        }

        tableModelUser.setRowCount(0);
        ArrayList<UserDTO> users = userBLL.searchUsers(keyword);
        for (UserDTO q : users) {
            String chucvu = q.isAdmin() ? "Admin" : "Người dùng";
            tableModelUser.addRow(new Object[]{
                q.getId(),
                q.getName(),
                q.getEmail(),
                q.getPassword(),
                q.getFullname(),
                chucvu
            });
        }
    }
    
}
