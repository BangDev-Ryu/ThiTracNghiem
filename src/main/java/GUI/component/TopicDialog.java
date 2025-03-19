package GUI.component;

import BLL.TopicBLL;
import DTO.TopicDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.*;


public class TopicDialog extends JDialog {
    private TopicBLL topicBLL = new TopicBLL();
    private final int WIDTH = 300, HEIGHT = 300;
    
    private String name;
    private int id;
    
    private JPanel pnForm, pnButton;
    private JComboBox cbParent;
    private JTextField tfName;
    private JButton btnSubmit, btnCancel;
    
    public TopicDialog(String name, int id) {
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
        pn.setPreferredSize(new Dimension(WIDTH, 200));

        
        // content layout
        JPanel pn_name = new JPanel(new FlowLayout(0, 15, 10));
        pn_name.setPreferredSize(new Dimension(WIDTH, 50));
        
        this.tfName = new JTextField("");
        this.tfName.setPreferredSize(new Dimension(170, 30));
        
        pn_name.add(new JLabel("Name: "));
        pn_name.add(tfName);
        
        // level layout
        JPanel pn_parent = new JPanel(new FlowLayout(0, 15, 10));
        pn_parent.setPreferredSize(new Dimension(WIDTH, 50));
        
        // Lấy danh sách parent từ cơ sở dữ liệu
        ArrayList<String> parentIds = topicBLL.getAllParents();
        
        
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String parentId : parentIds) {
            model.addElement(parentId);
        }
        this.cbParent = new JComboBox<>(model);
        pn_parent.add(new JLabel("Parent: "));
        pn_parent.add(cbParent);
        if (this.id != -1) { // Sửa
            TopicDTO topic = topicBLL.getTopicById(this.id);
            TopicDTO topicParent = topicBLL.getTopicById(topic.getParent());
            String parent;
            if(topic.getParent() != 0){
                parent = topic.getParent() + "-" + topicParent.getName();
            }else{
                parent = "0";
            }
            this.tfName.setText(topic.getName());
            this.cbParent.setSelectedItem(parent);
        }
        pn.setLayout(new FlowLayout(1));
        pn.add(pn_name);
        pn.add(pn_parent);
        
        return pn;
    }
    
    public JPanel initPanelButton() {
        JPanel pn = new JPanel();
        pn.setPreferredSize(new Dimension(500, 100));
        
        this.btnSubmit = new JButton("Xác nhận");
        this.btnCancel = new JButton("Hủy");
        
        pn.setLayout(new FlowLayout(1, 50, 50));
        pn.add(btnSubmit);
        pn.add(btnCancel);
        
        btnSubmit.addActionListener(e -> {
            if (tfName.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                return;
            }
            int parent = Integer.parseInt( (cbParent.getSelectedItem().toString()).split("-")[0] );
            String nameTopic = tfName.getText();
            TopicDTO topicDTO = new TopicDTO(id,nameTopic, parent, true);
//            
            if (id != -1) { // sửa
                topicBLL.updateTopic(topicDTO);
            }
            else { // thêm
               
                topicBLL.addTopic(topicDTO);
            }
            dispose();
        });
        
        btnCancel.addActionListener(e -> {
            dispose();
        });
        
        return pn;
    }
    
}
