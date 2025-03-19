package GUI;

import DAL.UserDAL;
import DTO.UserDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class RegisterGUI extends JFrame {
    private JPanel pn_header;
    private JPanel pn_center;
    private JPanel pn_south;
    private JTextField tf_name;
    private JTextField tf_email;
    private JPasswordField tf_password;
    private JPasswordField tf_confirm_password; // Thêm trường xác nhận mật khẩu
    private JTextField tf_fullname;
    private JButton btn_register;
    private JButton btn_back; // Nút quay lại
    private JLabel lb_error_noti;

    public RegisterGUI() {
        init();
    }

    public void init() {
        this.setUndecorated(true);
        this.setSize(400, 550); // Tăng kích thước cửa sổ
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pn_header = pnHeader();
        this.pn_center = pnCenter();
        this.pn_south = pnSouth();

        this.setLayout(new BorderLayout());
        this.add(pn_header, BorderLayout.NORTH);
        this.add(pn_center, BorderLayout.CENTER);
        this.add(pn_south, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public JPanel pnCenter() {
        JPanel pn_ct = new JPanel();
        pn_ct.setPreferredSize(new Dimension(300, 300));
        pn_ct.setLayout(new BorderLayout());

        // Textfield user and password
        JPanel pn_textfield = new JPanel();
        pn_textfield.setPreferredSize(new Dimension(200, 200));
        pn_textfield.setLayout(new FlowLayout(1, 40, 30));

        Dimension d_txtfield = new Dimension(250, 30);
        this.tf_name = new JTextField();
        this.tf_name.setPreferredSize(d_txtfield);
        this.tf_email = new JTextField();
        this.tf_email.setPreferredSize(d_txtfield);
        this.tf_password = new JPasswordField();
        this.tf_password.setPreferredSize(d_txtfield);
        this.tf_confirm_password = new JPasswordField(); // Trường xác nhận mật khẩu
        this.tf_confirm_password.setPreferredSize(d_txtfield);
        this.tf_fullname = new JTextField();
        this.tf_fullname.setPreferredSize(d_txtfield);

        JPanel pn_name = new JPanel();
        pn_name.setLayout(new FlowLayout());
        pn_name.setPreferredSize(new Dimension(250, 50));
        JPanel pn_email = new JPanel();
        pn_email.setLayout(new FlowLayout());
        pn_email.setPreferredSize(new Dimension(250, 50));
        JPanel pn_password = new JPanel();
        pn_password.setLayout(new FlowLayout());
        pn_password.setPreferredSize(new Dimension(250, 50));
        JPanel pn_confirm_password = new JPanel(); // Panel cho trường xác nhận mật khẩu
        pn_confirm_password.setLayout(new FlowLayout());
        pn_confirm_password.setPreferredSize(new Dimension(250, 50));
        JPanel pn_fullname = new JPanel();
        pn_fullname.setLayout(new FlowLayout());
        pn_fullname.setPreferredSize(new Dimension(250, 50));

        Font font_user_pass = new Font("Arial", Font.CENTER_BASELINE, 13);
        JLabel lb_name = new JLabel("Name");
        lb_name.setPreferredSize(new Dimension(250, 10));
        lb_name.setForeground(Color.decode("#3498db"));
        lb_name.setFont(font_user_pass);

        JLabel lb_email = new JLabel("Email");
        lb_email.setPreferredSize(new Dimension(250, 10));
        lb_email.setForeground(Color.decode("#3498db"));
        lb_email.setFont(font_user_pass);

        JLabel lb_password = new JLabel("Password");
        lb_password.setPreferredSize(new Dimension(250, 10));
        lb_password.setForeground(Color.decode("#3498db"));
        lb_password.setFont(font_user_pass);

        JLabel lb_confirm_password = new JLabel("Confirm Password"); // Nhãn cho trường xác nhận mật khẩu
        lb_confirm_password.setPreferredSize(new Dimension(250, 10));
        lb_confirm_password.setForeground(Color.decode("#3498db"));
        lb_confirm_password.setFont(font_user_pass);

        JLabel lb_fullname = new JLabel("Full Name");
        lb_fullname.setPreferredSize(new Dimension(250, 10));
        lb_fullname.setForeground(Color.decode("#3498db"));
        lb_fullname.setFont(font_user_pass);

        pn_name.add(lb_name);
        pn_name.add(this.tf_name);
        pn_email.add(lb_email);
        pn_email.add(this.tf_email);
        pn_password.add(lb_password);
        pn_password.add(this.tf_password);
        pn_confirm_password.add(lb_confirm_password); // Thêm trường xác nhận mật khẩu vào panel
        pn_confirm_password.add(this.tf_confirm_password);
        pn_fullname.add(lb_fullname);
        pn_fullname.add(this.tf_fullname);

        pn_textfield.add(pn_name);
        pn_textfield.add(pn_email);
        pn_textfield.add(pn_password);
        pn_textfield.add(pn_confirm_password); // Thêm trường xác nhận mật khẩu vào panel
        pn_textfield.add(pn_fullname);

        pn_ct.add(pn_textfield, BorderLayout.CENTER);

        return pn_ct;
    }

    public JPanel pnHeader() {
        JPanel hd = new JPanel();
        hd.setLayout(new BorderLayout());

        JLabel lb_back = new JLabel("←", JLabel.CENTER);
        lb_back.setOpaque(true);
        lb_back.setBackground(Color.decode("#FFFFFF"));
        lb_back.setPreferredSize(new Dimension(30, 30));

        lb_back.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginGUI(); // Quay lại giao diện đăng nhập
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lb_back.setBackground(Color.decode("#006270"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lb_back.setBackground(Color.decode("#FFFFFF"));
            }
        });

        hd.setPreferredSize(new Dimension(400, 30));
        hd.add(lb_back, BorderLayout.WEST);

        return hd;
    }
    
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public JPanel pnSouth() {
        JPanel pn_footer = new JPanel();
        pn_footer.setPreferredSize(new Dimension(400, 100));
        pn_footer.setLayout(new FlowLayout(1, 50, 20));

        // Nút đăng ký
        this.btn_register = new JButton("Register");
        this.btn_register.setPreferredSize(new Dimension(100, 30));
        this.btn_register.setBackground(Color.decode("#3498db"));
        this.btn_register.setForeground(Color.decode("#FFFFFF"));

        Font font = new Font("Arial", Font.BOLD, 11);
        this.lb_error_noti = new JLabel("", JLabel.CENTER);
        this.lb_error_noti.setFont(font);
        this.lb_error_noti.setForeground(Color.red);
        this.lb_error_noti.setPreferredSize(new Dimension(300, 30));

        Font font_btn = new Font("Arial", Font.BOLD, 15);
        this.btn_register.setFont(font_btn);

        btn_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tf_name.getText().trim();
                String email = tf_email.getText().trim();
                String password = new String(tf_password.getPassword());
                String confirmPassword = new String(tf_confirm_password.getPassword());
                String fullname = tf_fullname.getText().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
                    lb_error_noti.setText("Hãy điền hết vào các chỗ trống!");
                    return;
                }
                
                if (!isValidEmail(email)) {
                    lb_error_noti.setText("Định dạng email không đúng!");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    lb_error_noti.setText("Mật khẩu không giống nhau!");
                    return;
                }
                
                UserDTO newUser = new UserDTO(0, name, email, password, fullname, false);

                UserDAL userDAL = new UserDAL();
                
                if (userDAL.isEmailExists(email)) {
                    lb_error_noti.setText("Email đã tồn tại! Hãy đăng ký email khác!");
                    return;
                }
                
                if (userDAL.addUser(newUser)) {
                    JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
                    dispose();
                    new LoginGUI(); 
                } else {
                    lb_error_noti.setText("Đăng ký thất bại. Hãy thử lại sau!");
                }
            }
        });
        
        pn_footer.add(this.btn_register);
        pn_footer.add(this.lb_error_noti);

        return pn_footer;
    }
}
