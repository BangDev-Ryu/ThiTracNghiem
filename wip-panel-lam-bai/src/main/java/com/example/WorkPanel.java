package com.example;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public class WorkPanel extends JPanel {

    private TheTest theTest;

    private WorkAreaPanel workAreaPanel;
    private WorkInfoPanel workInfoPanel;

    public WorkPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(workInfoPanel = new WorkInfoPanel());
        add(workAreaPanel = new WorkAreaPanel());
        add(new WorkControlPanel());

        // TODO: tam thoi view question 1 de test truoc
        var qList = Util.readCsvToQuestions("./questions.csv", true);
        var tTest = new TheTest();
        tTest.setQuestions(qList);
        initializeTheTest(tTest);
    }

    public void initializeTheTest(TheTest theTest) {
        this.theTest = theTest;
        // TODO: tam thoi view question 1 de test truoc
        workAreaPanel.setViewQuestion(theTest.getQuestions().get(0));
        // TODO: can phai xac dinh duoc thu tu cau hoi
        workInfoPanel.setQuestions(
            IntStream.range(0, theTest.getQuestions().size())
                .mapToObj(index -> new QuestionInWork(theTest.getQuestions().get(index), index))
                .collect(Collectors.toList())
        );
    }

    public TheTest getTheTest() {
        return theTest;
    }
}

class WorkInfoPanel extends JPanel {

    private DefaultListModel<QuestionInWork> navigationModel = new DefaultListModel<>();
    private JList<QuestionInWork> navigationComponent;

    public WorkInfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel WorkInfoPanel"));
        add(navigationComponent = new JList<>());
        navigationComponent.setModel(navigationModel);
        navigationComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        navigationComponent.setCellRenderer(
            new ListCellRenderer<QuestionInWork>() {
                @Override
                public Component getListCellRendererComponent(
                    JList<? extends QuestionInWork> list,
                    QuestionInWork value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus
                ) {
                    JLabel label = new JLabel();
                    label.setText(
                        String.format(
                            "Question %d. %s",
                            value.getOrder(),
                            value.getSelectedAnswersStringRepresentation()
                        )
                    );
                    if (isSelected) {
                        label.setBackground(list.getSelectionBackground());
                        label.setForeground(list.getSelectionForeground());
                    } else {
                        label.setBackground(list.getBackground());
                        label.setForeground(list.getForeground());
                    }
                    label.setForeground(value.isAnyAnswerSelected() ? Color.GREEN : Color.RED);
                    if (value.isFocused()) {
                        label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | Font.BOLD));
                    }
                    label.setOpaque(true);
                    return label;
                }
            }
        );
    }

    // phai sort truoc, vi order se duoc lay de danh so thu tu
    public void setQuestions(List<QuestionInWork> questions) {
        if (navigationModel != null) {
            navigationModel.clear();
        }
        // Sort questions by order and add to model
        navigationModel.addAll(
            questions.stream().sorted(Comparator.comparingInt(QuestionInWork::getOrder)).collect(Collectors.toList())
        );
        navigationComponent.setSelectedIndex(0);
    }

    // dung de lang nghe su kien user chon cau hoi khac tren navigation
    // TODO: xai cai nay de doi cau hoi o panel giua
    public void addNavigationChangeListener(Consumer<QuestionInWork> listener) {
        navigationComponent.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = navigationComponent.getSelectedIndex();
                if (selectedIndex != -1) {
                    QuestionInWork selectedQuestion = navigationModel.getElementAt(selectedIndex);
                    listener.accept(selectedQuestion);
                }
            }
        });
    }
}

class WorkAreaPanel extends JPanel {

    private WebViewPanel webViewPanel;

    public WorkAreaPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel WorkAreaPanel"));
        add(webViewPanel = new WebViewPanel());
    }

    public void setViewQuestion(Question question) {
        webViewPanel.setViewContent(Util.generateQuestionHtml(question));
    }
}

class WorkControlPanel extends JPanel {

    public WorkControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel WorkControlPanel"));
    }
}

class WebViewPanel extends JPanel {

    private WebView webView;

    public WebViewPanel() {
        JFXPanel jfxPanel = new JFXPanel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(jfxPanel);

        Platform.runLater(() -> {
            webView = new WebView();
            webView.getEngine().loadContent("<html><body><h1>Hello HTML5!</h1></body></html>");
            Scene scene = new Scene(webView);
            jfxPanel.setScene(scene);
        });
    }

    public void setViewContent(String content) {
        Platform.runLater(() -> webView.getEngine().loadContent(content));
    }
}

class AnswerInWork {

    private Answer answer;
    private boolean isSelected = false;

    public AnswerInWork(Answer answer) {
        this.answer = answer;
    }

    public int getOrder() {
        return answer.getOrder();
    }

    public Answer getAnswer() {
        return answer;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}

class QuestionInWork {

    private Question question;
    private boolean isFocused = false;
    private List<AnswerInWork> answers;
    // neu ko co thi ko biet dc thu tu cau nao truoc sau
    private int order;

    public QuestionInWork(Question question, int order) {
        this.question = question;
        this.answers = question
            .getAnswers()
            .stream()
            .sorted(Comparator.comparingInt(Answer::getOrder))
            .map(AnswerInWork::new)
            .toList();
        this.order = order;
    }

    // cau nay co tra loi chua?
    public boolean isAnyAnswerSelected() {
        return answers.stream().anyMatch(AnswerInWork::isSelected);
    }

    // lay chuoi hien nhanh tren navigation
    // vd chon cau A va C (order 0 va 2) thi tra ve AC
    public String getSelectedAnswersStringRepresentation() {
        return answers
            .stream()
            .filter(AnswerInWork::isSelected)
            .map(AnswerInWork::getAnswer)
            .map(Answer::getExcelColumnName)
            .collect(Collectors.joining());
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public List<AnswerInWork> getAnswers() {
        return answers;
    }

    public int getOrder() {
        return order;
    }
}
