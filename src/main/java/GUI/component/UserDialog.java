package GUI.component;

import BLL.UserBLL;
import DTO.UserDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserDialog extends JDialog {
    private UserBLL userBLL = new UserBLL();
    private final int WIDTH = 500, HEIGHT = 400;
    
    private String name;
    private int id;
    
    private JPanel pnForm, pnButton;
    private JTextField tfName, tfEmail, tfPassword, tfFullname;
    private JComboBox cbAdmin;
    private JButton btnSubmit, btnCancel;
    
    public UserDialog(String name, int id) {
        this.name = name;
        this.id = id;
        
        init();
    }
    
    public void init() {
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setTitle(name);
        this.setModal(true);
        
        this.pnForm = initPanelForm();
        this.pnButton = initPanelButton();
        
        this.setLayout(new BorderLayout());
        this.add(pnForm, BorderLayout.CENTER);
        this.add(pnButton, BorderLayout.SOUTH);
        
        this.setVisible(true);
    }
    
    public JPanel initPanelForm() {
        JPanel pn = new JPanel();
        pn.setLayout(new GridLayout(6, 2, 10, 10));
        pn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        tfName = new JTextField(10);
        tfEmail = new JTextField(10);
        tfPassword = new JTextField(10);
        tfFullname = new JTextField(10);
        cbAdmin = new JComboBox<>(new String[]{"Người Dùng", "Admin"});
        
        pn.add(new JLabel("Tên: "));
        pn.add(tfName);
        
        pn.add(new JLabel("Email: "));
        pn.add(tfEmail);
        
        pn.add(new JLabel("Mật khẩu: "));
        pn.add(tfPassword);
        
        pn.add(new JLabel("Họ tên: "));
        pn.add(tfFullname);
        
        pn.add(new JLabel("Chức vụ: "));
        pn.add(cbAdmin);
        
        if (this.id != -1) { 
            UserDTO user = userBLL.getUserById(this.id);
            tfName.setText(user.getName());
            tfEmail.setText(user.getEmail());
            tfPassword.setText(user.getPassword());
            tfFullname.setText(user.getFullname());
            cbAdmin.setSelectedIndex(user.isAdmin() ? 1 : 0);
        }
        
        return pn;
    }
    
    public JPanel initPanelButton() {
        JPanel pn = new JPanel();
        pn.setPreferredSize(new Dimension(WIDTH, 50));
        
        btnSubmit = new JButton("Xác nhận");
        btnCancel = new JButton("Hủy");
        
        pn.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pn.add(btnSubmit);
        pn.add(btnCancel);
        
        btnSubmit.addActionListener(e -> {
            if (tfName.getText().trim().isEmpty() || tfEmail.getText().trim().isEmpty() || tfPassword.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                return;
            }
            
            String name = tfName.getText();
            String email = tfEmail.getText();
            String password = tfPassword.getText();
            String fullname = tfFullname.getText();
            boolean isAdmin = cbAdmin.getSelectedIndex() == 1;
            
            UserDTO userDTO = new UserDTO(id, name, email, password, fullname, isAdmin);
            
            if (id != -1) { // sửa
                userBLL.updateUser(userDTO);
            } else { // thêm
                userBLL.addUser(userDTO);
            }
            dispose();
        });
        
        btnCancel.addActionListener(e -> dispose());
        
        return pn;
    }
    
}
