package GUI.component;

import javax.swing.*;
import java.awt.*;

public class StatisticsDialog extends JDialog {

    private final int WIDTH = 400, HEIGHT = 300;

    private JPanel pnContent;
    private JLabel lblTotalQuestions, lblCorrectAnswers, lblWrongAnswers, lblAverageScore, lblTimeSpent;
    private JButton btnClose;

    public StatisticsDialog(String title) {
        this.setTitle(title);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        init();
    }

    public void init() {
        pnContent = new JPanel();
        pnContent.setLayout(new GridLayout(5, 2, 10, 10));

        // Thống kê tổng số câu hỏi
        lblTotalQuestions = new JLabel("Total Questions: ");
        lblTotalQuestions.setHorizontalAlignment(JLabel.RIGHT);
        JLabel lblTotalQuestionsValue = new JLabel("100");  // Thay bằng giá trị động từ cơ sở dữ liệu

        // Thống kê số câu hỏi đúng
        lblCorrectAnswers = new JLabel("Correct Answers: ");
        lblCorrectAnswers.setHorizontalAlignment(JLabel.RIGHT);
        JLabel lblCorrectAnswersValue = new JLabel("80");  // Thay bằng giá trị động từ cơ sở dữ liệu

        // Thống kê số câu hỏi sai
        lblWrongAnswers = new JLabel("Wrong Answers: ");
        lblWrongAnswers.setHorizontalAlignment(JLabel.RIGHT);
        JLabel lblWrongAnswersValue = new JLabel("20");  // Thay bằng giá trị động từ cơ sở dữ liệu

        // Thống kê điểm trung bình
        lblAverageScore = new JLabel("Average Score: ");
        lblAverageScore.setHorizontalAlignment(JLabel.RIGHT);
        JLabel lblAverageScoreValue = new JLabel("8.0");  // Thay bằng giá trị động từ cơ sở dữ liệu

        // Thống kê thời gian làm bài
        lblTimeSpent = new JLabel("Time Spent: ");
        lblTimeSpent.setHorizontalAlignment(JLabel.RIGHT);
        JLabel lblTimeSpentValue = new JLabel("45 minutes");  // Thay bằng giá trị động từ cơ sở dữ liệu

        // Add components to panel
        pnContent.add(lblTotalQuestions);
        pnContent.add(lblTotalQuestionsValue);
        pnContent.add(lblCorrectAnswers);
        pnContent.add(lblCorrectAnswersValue);
        pnContent.add(lblWrongAnswers);
        pnContent.add(lblWrongAnswersValue);
        pnContent.add(lblAverageScore);
        pnContent.add(lblAverageScoreValue);
        pnContent.add(lblTimeSpent);
        pnContent.add(lblTimeSpentValue);

        // Close button
        btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            this.dispose();
        });

        this.add(pnContent, BorderLayout.CENTER);
        this.add(btnClose, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Test the StatisticsDialog
        new StatisticsDialog("Statistics");
    }
}
