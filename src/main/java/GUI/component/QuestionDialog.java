package GUI.component;

import BLL.QuestionBLL;
import DTO.QuestionDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QuestionDialog extends JDialog {
    private QuestionBLL questionBLL = new QuestionBLL();
    private final int WIDTH = 500, HEIGHT = 700;
    
    private String name;
    private int id;
    
    private JPanel pnForm, pnButton;
    private JComboBox cbTopic, cbLevel;
    private JTextArea taContent;
    private JButton btnSubmit, btnCancel;
    
    public QuestionDialog(String name, int id) {
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
        pn.setPreferredSize(new Dimension(500, 500));

        Dimension size_panel = new Dimension(400, 100);
        
        // topic layout
        JPanel pn_topic = new JPanel(new FlowLayout(0, 15, 70));
        pn_topic.setPreferredSize(size_panel);
//        pn_topic.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        String[] topicItems = {"1", "2", "3"};
        this.cbTopic = new JComboBox(topicItems);
        
        pn_topic.add(new JLabel("Chủ đề: "));
        pn_topic.add(cbTopic);
        
        // content layout
        JPanel pn_content = new JPanel(new FlowLayout(0, 15, 10));
        pn_content.setPreferredSize(size_panel);
//        pn_content.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        this.taContent = new JTextArea("");
        this.taContent.setPreferredSize(new Dimension(300, 80));
        
        pn_content.add(new JLabel("Nội dung: "));
        pn_content.add(taContent);
        
        // level layout
        JPanel pn_level = new JPanel(new FlowLayout(0, 15, 10));
        pn_level.setPreferredSize(size_panel);
//        pn_level.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        String[] levelItems = {"Easy", "Normal", "Hard"};
        this.cbLevel = new JComboBox(levelItems);
        
        pn_level.add(new JLabel("Cấp độ: "));
        pn_level.add(cbLevel);
        
        if (this.id != -1) { // Sửa
            QuestionDTO question = questionBLL.getQuestionById(this.id);
            this.cbTopic.setSelectedItem(question.getTopicId());
            this.cbTopic.setSelectedItem(question.getLevel());
            this.taContent.setText(question.getContent());
        }
        else { // Thêm
            
        }
        
        pn.setLayout(new FlowLayout(1));
        pn.add(pn_topic);
        pn.add(pn_content);
        pn.add(pn_level);
        
        return pn;
    }
    
    public JPanel initPanelButton() {
        JPanel pn = new JPanel();
        pn.setPreferredSize(new Dimension(500, 200));
        
        this.btnSubmit = new JButton("Xác nhận");
        this.btnCancel = new JButton("Hủy");
        
        pn.setLayout(new FlowLayout(1, 50, 150));
        pn.add(btnSubmit);
        pn.add(btnCancel);
        
        btnSubmit.addActionListener(e -> {
            if (taContent.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                return;
            }
            
            int id_topic = Integer.parseInt(cbTopic.getSelectedItem().toString());
            String content = taContent.getText();
            String picture = "";
            String level = (String)cbLevel.getSelectedItem();
            
            QuestionDTO questionDTO = new QuestionDTO(id, id_topic, content, picture, level, true);
            
            if (id != -1) { // sửa
                questionBLL.updateQuestion(questionDTO);
            }
            else { // thêm
                questionBLL.addQuestion(questionDTO);
            }
            dispose();
        });
        
        btnCancel.addActionListener(e -> {
            dispose();
        });
        
        return pn;
    }
    
}
