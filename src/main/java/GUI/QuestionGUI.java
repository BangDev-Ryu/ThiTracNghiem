package GUI;

import BLL.AnswerBLL;
import BLL.QuestionBLL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import GUI.component.AnswerDialog;
import GUI.component.QuestionDialog;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class QuestionGUI extends JPanel {
    private QuestionBLL questionBLL = new QuestionBLL();
    private AnswerBLL answerBLL = new AnswerBLL();
    
    private int width, height;
    private JTable tableQuestion, tableAnswer;
    private DefaultTableModel tableModelQuestion, tableModelAnswer;
    
    private JPanel pnQuestion, pnAnswer, pnFilter;
    private JTextField tfSearch;
    private JComboBox cbTopic, cbLevel;
    private JButton btnSearch;
    private JButton btnAddQuest, btnUpdateQuest, btnDelQuest;
    private JButton btnAddAns, btnUpdateAns, btnDelAns;
    
    public QuestionGUI(int width, int height) {
        this.width = width;
        this.height = height;
        init();
    }
    
    public void init() {
        this.setSize(this.width, this.height);
        
        this.pnAnswer = initPanelAnswer();
        this.pnFilter = initPanelFilter();
        this.pnQuestion = initPanelQuestion();
        
        this.setLayout(new BorderLayout());
        
        this.add(pnAnswer, BorderLayout.NORTH);
        this.add(pnFilter, BorderLayout.CENTER);
        this.add(pnQuestion, BorderLayout.SOUTH);
    }
    
    public JPanel initPanelAnswer() {
        JPanel pn = new JPanel(new BorderLayout());
        pn.setPreferredSize(new Dimension(this.width, 300));
//        pn.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        // table layout
        JPanel pn_table = new JPanel(new FlowLayout(1, 30, 25));
        pn_table.setPreferredSize(new Dimension(800, 300));
//        pn_table.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
        
        tableModelAnswer = new DefaultTableModel(new Object[]{
            "ID", "Nội dung", "Đúng", "Ảnh"
        }, 0);
        tableAnswer = new JTable(tableModelAnswer);
        JScrollPane scrollPane = new JScrollPane(tableAnswer);
        scrollPane.setPreferredSize(new Dimension(780, 250));
        
        pn_table.add(scrollPane);
        
        // button layout
        JPanel pn_btn = new JPanel(new FlowLayout(0, 20, 30));
        pn_btn.setPreferredSize(new Dimension(200, 250));
        
        this.btnAddAns = new JButton("Thêm câu trả lời");
        this.btnUpdateAns = new JButton("Sửa câu trả lời");
        this.btnDelAns = new JButton("Xóa câu trả lời");
        
        Dimension btn_size = new Dimension(140, 40);
        Font btn_font = new Font("SansSerif", Font.BOLD, 12);
        
        this.btnAddAns.setPreferredSize(btn_size);
        this.btnUpdateAns.setPreferredSize(btn_size);
        this.btnDelAns.setPreferredSize(btn_size);
        
        this.btnAddAns.setFont(btn_font);
        this.btnUpdateAns.setFont(btn_font);
        this.btnDelAns.setFont(btn_font);
        
        pn_btn.add(btnAddAns);
        pn_btn.add(btnUpdateAns);
        pn_btn.add(btnDelAns);
        
        pn.add(pn_table, BorderLayout.CENTER);
        pn.add(pn_btn, BorderLayout.EAST);
        
        this.btnAddAns.addActionListener(e -> addAnswer());
        this.btnUpdateAns.addActionListener(e -> updateAnswer());
        this.btnDelAns.addActionListener(e -> deleteAnswer());
        
        return pn;
    }
    
    public JPanel initPanelFilter() {
        JPanel pn = new JPanel();
        pn.setPreferredSize(new Dimension(this.width, 100));
        pn.setLayout(new FlowLayout(1, 0, 0));
        
        Dimension btn_size = new Dimension(120, 30);
        Font btn_font = new Font("SansSerif", Font.BOLD, 12);
        
        // filter layout
        JPanel pn_filter = new JPanel(new FlowLayout(1, 10, 50));
        pn_filter.setPreferredSize(new Dimension(this.width/2 + 80, 100));
//        pn_filter.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        this.tfSearch = new JTextField();
        this.tfSearch.setPreferredSize(new Dimension(150, 30));
        
        this.cbTopic = new JComboBox();
        this.cbTopic.setPreferredSize(new Dimension(100, 30));
        
        this.cbLevel = new JComboBox();
        this.cbLevel.setPreferredSize(new Dimension(100, 30));
        
        this.btnSearch = new JButton("Tìm kiếm");
        this.btnSearch.setPreferredSize(new Dimension(100, 30));
        this.btnSearch.setFont(btn_font);
        
        pn_filter.add(tfSearch);
        pn_filter.add(cbTopic);
        pn_filter.add(cbLevel);
        pn_filter.add(btnSearch);
        
        // button layout
        JPanel pn_btn = new JPanel(new FlowLayout(0, 20, 50));
        pn_btn.setPreferredSize(new Dimension(this.width/2 - 80, 100));
        
        this.btnAddQuest = new JButton("Thêm câu hỏi");
        this.btnUpdateQuest = new JButton("Sửa câu hỏi");
        this.btnDelQuest = new JButton("Xóa câu hỏi");
        
        this.btnAddQuest.setPreferredSize(btn_size);
        this.btnUpdateQuest.setPreferredSize(btn_size);
        this.btnDelQuest.setPreferredSize(btn_size);
        
        this.btnAddQuest.setFont(btn_font);
        this.btnUpdateQuest.setFont(btn_font);
        this.btnDelQuest.setFont(btn_font);
        
        pn_btn.add(btnAddQuest);
        pn_btn.add(btnUpdateQuest);
        pn_btn.add(btnDelQuest);
        
        this.btnAddQuest.addActionListener(e -> addQuestion());
        this.btnUpdateQuest.addActionListener(e -> updateQuestion());
        this.btnDelQuest.addActionListener(e -> deleteQuestion());
        
        pn.add(pn_filter);
        pn.add(pn_btn);
        
        return pn;
    }
    
    public JPanel initPanelQuestion() {
        JPanel pn = new JPanel(new FlowLayout(1, 0, 0));
        pn.setPreferredSize(new Dimension(this.width, 320));
//        pn.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        tableModelQuestion = new DefaultTableModel(new Object[]{
            "ID", "Chủ đề", "Nội dung", "Cấp độ", "Ảnh"
        }, 0);
        tableQuestion = new JTable(tableModelQuestion);
        tableQuestion.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(tableQuestion);
        scrollPane.setPreferredSize(new Dimension(1000, 250));
        
        pn.add(scrollPane);
        
        loadQuestions();
        
        tableQuestion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableQuestion.getSelectedRow();
                int questionId = (int) tableModelQuestion.getValueAt(selectedRow, 0);
                loadAnswers(questionId);
            }
        });
        
        return pn;
    }
    
    public void loadQuestions() {
        this.tableModelQuestion.setRowCount(0);
        ArrayList<QuestionDTO> questions = questionBLL.getAllQuestions();
        for (QuestionDTO q : questions) {
            tableModelQuestion.addRow(new Object[] {
                q.getId(),
                q.getTopicId(),
                q.getContent(),
                q.getLevel(),
                q.getPicture()
            });
        }
    }
    
    public void addQuestion() {
        new QuestionDialog("Thêm câu hỏi", -1);
        loadQuestions();
    }
    
    public void updateQuestion() {
        int selectedRow = tableQuestion.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModelQuestion.getValueAt(selectedRow, 0);
            new QuestionDialog("Sửa câu hỏi", id);
            loadQuestions();
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn câu hỏi cần sửa");
            return;
        }
        
    }
    
    public void deleteQuestion() {
        int selectedRow = tableQuestion.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModelQuestion.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                questionBLL.deleteQuestion(id);
                loadQuestions();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn câu hỏi cần xóa");
            return;
        }
    }
    
    public void loadAnswers(int id) {
        this.tableModelAnswer.setRowCount(0);
        ArrayList<AnswerDTO> answers = answerBLL.getAnswersByQuestionId(id);
        for (AnswerDTO a : answers) {
            tableModelAnswer.addRow(new Object[] {
                a.getId(),
                a.getContent(),
                a.isRight(),
                a.getPicture()
            });
        }
    }
    
    public void addAnswer() {
        int selectedRow = tableQuestion.getSelectedRow();
        if (selectedRow != -1) {
            int questionId = (int) tableModelQuestion.getValueAt(selectedRow, 0);
            new AnswerDialog("Thêm câu trả lời", -1, questionId);
            loadAnswers(questionId);
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn câu hỏi cần thêm câu trả lời");
            return;
        }
    }
    
    public void updateAnswer() {
        int selectedRow = tableQuestion.getSelectedRow();
        if (selectedRow != -1) {
            int questionId = (int) tableModelQuestion.getValueAt(selectedRow, 0);
            int answerRow = tableAnswer.getSelectedRow();
            
            if (answerRow != -1) {
                int id = (int) tableModelAnswer.getValueAt(answerRow, 0);
                new AnswerDialog("Sửa câu trả lời", id, questionId);
                loadAnswers(questionId);
            }
            else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn câu trả lời cần sửa");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn câu hỏi cần sửa câu trả lời");
            return;
        }
    }
    
    public void deleteAnswer() {
        int selectedRow = tableQuestion.getSelectedRow();
        if (selectedRow != -1) {
            int questionId = (int) tableModelQuestion.getValueAt(selectedRow, 0);
            
            int answerRow = tableAnswer.getSelectedRow();
            if (answerRow != -1) {
                int id = (int) tableModelAnswer.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Xác nhận xóa", "Alert", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    answerBLL.deleteAnswer(id);
                    loadAnswers(questionId);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn câu trả lời cần xóa");
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn câu hỏi cần xóa câu trả lời");
            return;
        }
        
        
    }
}
