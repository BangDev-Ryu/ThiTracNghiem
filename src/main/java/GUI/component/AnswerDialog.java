package GUI.component;

import BLL.AnswerBLL;
import DTO.AnswerDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AnswerDialog extends JDialog {
    private AnswerBLL answerBLL = new AnswerBLL();
    private final int WIDTH = 500, HEIGHT = 700;
    
    private String name;
    private int id, questionId;
    
    private JPanel pnForm, pnButton;
    private JComboBox cbIsRight;
    private JTextArea taContent;
    private JButton btnSubmit, btnCancel;
    
    public AnswerDialog(String name, int id, int questionId) {
        this.name = name;
        this.id = id;
        this.questionId = questionId;
        
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
        
        // content layout
        JPanel pn_content = new JPanel(new FlowLayout(0, 15, 10));
        pn_content.setPreferredSize(size_panel);
        
        this.taContent = new JTextArea("");
        this.taContent.setPreferredSize(new Dimension(300, 80));
        
        pn_content.add(new JLabel("Nội dung: "));
        pn_content.add(taContent);
        
        // is right layout
        JPanel pn_is_right = new JPanel(new FlowLayout(0, 15, 10));
        pn_is_right.setPreferredSize(size_panel);
        
        String[] levelItems = {"Đúng", "Sai"};
        this.cbIsRight = new JComboBox(levelItems);
        
        pn_is_right.add(new JLabel("Đáp án: "));
        pn_is_right.add(cbIsRight);
        
        if (this.id != -1) { // Sửa
            AnswerDTO answer = answerBLL.getAnswerById(id);
            this.taContent.setText(answer.getContent());
            if (answer.isRight()) {
                this.cbIsRight.setSelectedItem("Đúng");
            }
            else {
                this.cbIsRight.setSelectedItem("Sai");
            }
        }
        else { // Thêm
            
        }
        
        pn.setLayout(new FlowLayout(1));
        pn.add(pn_content);
        pn.add(pn_is_right);
        
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
            
            String content = taContent.getText();
            String picture = "";
            Boolean isRight = true;
            
            if (cbIsRight.getSelectedItem().toString().equals("Sai")) {
                isRight = false;
            }
            
            AnswerDTO answer = new AnswerDTO(id, questionId, content, picture, isRight, true);
            
            if (id != -1) { // sửa
                answerBLL.updateAnswer(answer);
            }
            else { // thêm
                answerBLL.addAnswer(answer);
            }
            dispose();
        });
        
        btnCancel.addActionListener(e -> {
            dispose();
        });
        
        return pn;
    }
}
