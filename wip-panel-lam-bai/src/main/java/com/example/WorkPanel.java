package com.example;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public class WorkPanel extends JPanel {

    private Exam theTest;
    private List<QuestionInWork> questions;
    private QuestionInWork currentQuestion;

    private WorkAreaPanel workAreaPanel;
    private WorkInfoPanel workInfoPanel;
    private WorkControlPanel workControlPanel;

    public WorkPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(workInfoPanel = new WorkInfoPanel());
        add(workAreaPanel = new WorkAreaPanel());
        add(workControlPanel = new WorkControlPanel());
    }

    public synchronized void setExam(Exam exam) {
        this.theTest = exam;
        questions = IntStream.range(0, exam.getQuestions().size())
            .mapToObj(index -> new QuestionInWork(exam.getQuestions().get(index), index))
            .toList();
        workInfoPanel.setQuestions(questions);
        if (questions.size() > 0) {
            currentQuestion = questions.get(0);
        }
        workInfoPanel.addNavigationFocusChangeListener(this::showQuestion);
        workAreaPanel.setViewQuestion(questions.get(0));
        workControlPanel.setViewAnswersForQuestion(questions.get(0));
        workControlPanel.addAnswersSelectedStateChangeListener(this::answerChanged);
        workControlPanel.addNextEventListener(() -> {
            int currentIndex = questions.indexOf(currentQuestion);
            if (currentIndex == -1) {
                return;
            }
            showQuestion(questions.get((currentIndex + 1) % questions.size()));
        });
        workControlPanel.addNextUnansweredEventListener(() -> {
            int currentIndex = questions.indexOf(currentQuestion);
            for (int i = 1; i < questions.size(); i++) {
                int nextIndex = (currentIndex + i) % questions.size();
                if (!questions.get(nextIndex).isAnyAnswerSelected()) {
                    showQuestion(questions.get(nextIndex));
                    return;
                }
            }
        });
        workInfoPanel.addRandomizeAllEventListener(() -> {
            questions.stream().forEach(QuestionInWork::randomizeSelectedAnswers);
            allAnswersChanged();
        });
        workInfoPanel.addRandomizeAllUnansweredEventListener(() -> {
            questions
                .stream()
                .filter(Predicate.not(QuestionInWork::isAnyAnswerSelected))
                .forEach(QuestionInWork::randomizeSelectedAnswers);
            allAnswersChanged();
        });
        workInfoPanel.addClearAllAnswersEventListener(() -> {
            questions.stream().forEach(QuestionInWork::deselectAnswer);
            allAnswersChanged();
        });
    }

    // chuyen sang cau hoi n, co the dc goi boi navigation hoac nut next
    public void showQuestion(QuestionInWork question) {
        currentQuestion = question;
        workInfoPanel.setFocusedQuestion(question);
        workAreaPanel.setViewQuestion(question);
        workControlPanel.setViewAnswersForQuestion(question);
    }

    // nguoi dung chon cau tra loi khac
    // answers panel da tu update nen khong can goi update cua no
    public void answerChanged(QuestionInWork question) {
        workInfoPanel.updateQuestion(question);
    }

    // nguoi dung chon chuc nang thay doi cau tra loi cua toan bo cac cau hoi
    // khi nay thi can update answers panel
    public void allAnswersChanged() {
        workControlPanel.setViewAnswersForQuestion(currentQuestion);
        workInfoPanel.updateAllQuestions();
    }

    public Exam getTheTest() {
        return theTest;
    }
}

class WorkInfoPanel extends JPanel {

    private QuestionNavigationPanel questionNavigationPanel;
    private ExamControlPanel examControlPanel;

    public WorkInfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel WorkInfoPanel"));
        add(questionNavigationPanel = new QuestionNavigationPanel());
        add(examControlPanel = new ExamControlPanel());
    }

    public synchronized void setQuestions(List<QuestionInWork> questions) {
        questionNavigationPanel.setQuestions(questions);
    }

    public void addNavigationFocusChangeListener(Consumer<QuestionInWork> listener) {
        questionNavigationPanel.addNavigationFocusChangeListener(listener);
    }

    public synchronized void setFocusedQuestion(QuestionInWork question) {
        questionNavigationPanel.setFocusedQuestion(question);
    }

    public void updateQuestion(QuestionInWork question) {
        questionNavigationPanel.updateQuestion(question);
    }

    public void updateAllQuestions() {
        questionNavigationPanel.updateAllQuestions();
    }

    public void addRandomizeAllEventListener(Runnable listener) {
        examControlPanel.addRandomizeAllEventListener(listener);
    }

    public void removeRandomizeAllEventListener(Runnable listener) {
        examControlPanel.removeRandomizeAllEventListener(listener);
    }

    public void addFinishExamEventListener(Runnable listener) {
        examControlPanel.addFinishExamEventListener(listener);
    }

    public void removeFinishExamEventListener(Runnable listener) {
        examControlPanel.removeFinishExamEventListener(listener);
    }

    public void addRandomizeAllUnansweredEventListener(Runnable listener) {
        examControlPanel.addRandomizeAllUnansweredEventListener(listener);
    }

    public void removeRandomizeAllUnansweredEventListener(Runnable listener) {
        examControlPanel.removeRandomizeAllUnansweredEventListener(listener);
    }

    public void addClearAllAnswersEventListener(Runnable listener) {
        examControlPanel.addClearAllAnswersEventListener(listener);
    }

    public void removeClearAllAnswersEventListener(Runnable listener) {
        examControlPanel.removeClearAllAnswersEventListener(listener);
    }
}

class QuestionNavigationPanel extends JPanel {

    private DefaultListModel<QuestionInWork> navigationModel = new DefaultListModel<>();
    private JList<QuestionInWork> navigationComponent;

    private AtomicBoolean isNavigationChangeByUser = new AtomicBoolean(true);

    public QuestionNavigationPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
                    label.setForeground(value.isAnyAnswerSelected() ? Color.BLACK : Color.RED);
                    if (value.isFocused()) {
                        label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | Font.BOLD));
                    }
                    label.setOpaque(true);
                    return label;
                }
            }
        );
    }

    public synchronized void setQuestions(List<QuestionInWork> questions) {
        if (navigationModel != null) {
            navigationModel.clear();
        }
        isNavigationChangeByUser.set(false);
        navigationModel.addAll(questions.stream().sorted(Comparator.comparingInt(QuestionInWork::getOrder)).toList());
        navigationComponent.setSelectedIndex(0);
        isNavigationChangeByUser.set(true);
    }

    // se rerender lai cell
    public void updateQuestion(QuestionInWork question) {
        int index = navigationModel.indexOf(question);
        if (index != -1) {
            navigationModel.set(index, question);
        }
    }

    // goi khi dung randomize all de render lai toan bo list
    public void updateAllQuestions() {
        // luu tam list de add lai vao model de ban su kien thay doi toan bo item
        List<QuestionInWork> temp = Collections.list(navigationModel.elements());
        QuestionInWork currentQuestion = navigationComponent.getSelectedValue();
        navigationModel.clear();
        navigationModel.addAll(temp);
        navigationComponent.setSelectedValue(currentQuestion, true);
    }

    public void addNavigationFocusChangeListener(Consumer<QuestionInWork> listener) {
        navigationComponent.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && isNavigationChangeByUser.get()) {
                int selectedIndex = navigationComponent.getSelectedIndex();
                if (selectedIndex != -1) {
                    QuestionInWork selectedQuestion = navigationModel.getElementAt(selectedIndex);
                    listener.accept(selectedQuestion);
                }
            }
        });
    }

    public synchronized void setFocusedQuestion(QuestionInWork question) {
        navigationComponent.getSelectedValue().setFocused(false);
        question.setFocused(true);
        isNavigationChangeByUser.set(false);
        navigationComponent.setSelectedValue(question, true);
        isNavigationChangeByUser.set(true);
    }
}

class ExamControlPanel extends JPanel {

    private JButton randomizeAllButton;
    private JButton randomizeAllUnansweredButton;
    private JButton clearAllAnswersButton;
    private JButton finishExamButton;

    private Map<Runnable, ActionListener> randomizeAllEventListenerMap = new HashMap<>();
    private Map<Runnable, ActionListener> randomizeAllUnansweredEventListenerMap = new HashMap<>();
    private Map<Runnable, ActionListener> clearAllAnswersEventListenerMap = new HashMap<>();
    private Map<Runnable, ActionListener> finishExamEventListenerMap = new HashMap<>();

    public ExamControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        randomizeAllButton = new JButton("Randomize All");
        randomizeAllUnansweredButton = new JButton("Randomize All Unanswered");
        clearAllAnswersButton = new JButton("Clear All Answers");
        finishExamButton = new JButton("Finish Exam");
        add(randomizeAllButton);
        add(randomizeAllUnansweredButton);
        add(clearAllAnswersButton);
        add(finishExamButton);
    }

    public void addRandomizeAllEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        randomizeAllEventListenerMap.put(listener, wrappedListener);
        randomizeAllButton.addActionListener(wrappedListener);
    }

    public void removeRandomizeAllEventListener(Runnable listener) {
        ActionListener wrappedListener = randomizeAllEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            randomizeAllButton.removeActionListener(wrappedListener);
        }
    }

    public void addRandomizeAllUnansweredEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        randomizeAllUnansweredEventListenerMap.put(listener, wrappedListener);
        randomizeAllUnansweredButton.addActionListener(wrappedListener);
    }

    public void removeRandomizeAllUnansweredEventListener(Runnable listener) {
        ActionListener wrappedListener = randomizeAllUnansweredEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            randomizeAllUnansweredButton.removeActionListener(wrappedListener);
        }
    }

    public void addClearAllAnswersEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        clearAllAnswersEventListenerMap.put(listener, wrappedListener);
        clearAllAnswersButton.addActionListener(wrappedListener);
    }

    public void removeClearAllAnswersEventListener(Runnable listener) {
        ActionListener wrappedListener = clearAllAnswersEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            clearAllAnswersButton.removeActionListener(wrappedListener);
        }
    }

    public void addFinishExamEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        finishExamEventListenerMap.put(listener, wrappedListener);
        finishExamButton.addActionListener(wrappedListener);
    }

    public void removeFinishExamEventListener(Runnable listener) {
        ActionListener wrappedListener = finishExamEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            finishExamButton.removeActionListener(wrappedListener);
        }
    }
}

class WorkAreaPanel extends JPanel {

    private QuestionViewPanel webViewPanel;

    public WorkAreaPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel WorkAreaPanel"));
        add(webViewPanel = new QuestionViewPanel());
    }

    public void setViewQuestion(QuestionInWork question) {
        webViewPanel.setViewContent(Util.generateQuestionHtml(question.getQuestion(), question.getOrder()));
    }
}

class AnswersChoicePanel extends JPanel {

    private QuestionInWork currentQuestion;

    private PropertyChangeSupport answersSelectedStateChange;
    private Map<Consumer<QuestionInWork>, PropertyChangeListener> answersSelectedStateChangeListenerMap;

    public AnswersChoicePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        answersSelectedStateChange = new PropertyChangeSupport(this);
        answersSelectedStateChangeListenerMap = new HashMap<>();
    }

    public void setQuestion(QuestionInWork question) {
        currentQuestion = question;
        boolean isMultipleChoices = question.getQuestion().isMultipleChoices();
        removeAll();
        ButtonGroup singleChoiceGroup = new ButtonGroup();
        for (AnswerInWork answerInWork : question.getAnswers()) {
            AbstractButton button;
            if (isMultipleChoices) {
                button = new JCheckBox(answerInWork.getAnswer().getExcelColumnName());
            } else {
                button = new JRadioButton(answerInWork.getAnswer().getExcelColumnName());
                singleChoiceGroup.add(button);
            }
            button.setSelected(answerInWork.isSelected());
            button.putClientProperty("answerInWork", answerInWork);
            button.addItemListener(_ -> {
                // sync the answers' selected state
                for (Component comp : getComponents()) {
                    if (comp instanceof JCheckBox || comp instanceof JRadioButton) {
                        AbstractButton btn = (AbstractButton) comp;
                        AnswerInWork ans = (AnswerInWork) btn.getClientProperty("answerInWork");
                        if (ans != null) {
                            ans.setSelected(btn.isSelected());
                        }
                    }
                }
                answersSelectedStateChange.firePropertyChange("answersSelectedState", null, question);
            });
            add(button);
        }
        revalidate();
        repaint();
    }

    public void addAnswersSelectedStateChangeListener(Consumer<QuestionInWork> listener) {
        PropertyChangeListener wrappedListener = evt -> {
            if (evt.getPropertyName() == "answersSelectedState") {
                listener.accept((QuestionInWork) evt.getNewValue());
            }
        };
        answersSelectedStateChangeListenerMap.put(listener, wrappedListener);
        answersSelectedStateChange.addPropertyChangeListener("answersSelectedState", wrappedListener);
    }

    public void removeAnswersSelectedStateChangeListener(Consumer<QuestionInWork> listener) {
        PropertyChangeListener wrappedListener = answersSelectedStateChangeListenerMap.remove(listener);
        if (wrappedListener != null) {
            answersSelectedStateChange.removePropertyChangeListener("answersSelectedState", wrappedListener);
        }
    }

    public void clearSelection() {
        if (currentQuestion != null) {
            currentQuestion.getAnswers().forEach(answer -> answer.setSelected(false));
        }
        // rerender
        setQuestion(currentQuestion);
        answersSelectedStateChange.firePropertyChange("answersSelectedState", null, currentQuestion);
    }

    public void randomizeSelection() {
        if (currentQuestion == null) {
            return;
        }
        currentQuestion.randomizeSelectedAnswers();
        // Rerender the panel
        setQuestion(currentQuestion);
        answersSelectedStateChange.firePropertyChange("answersSelectedState", null, currentQuestion);
    }
}

class AnswersControlPanel extends JPanel {

    private JButton clearButton;
    private JButton nextButton;
    private JButton nextUnansweredButton;
    private JButton randomizeButton;

    private Map<Runnable, ActionListener> nextEventListenerMap = new HashMap<>();
    private Map<Runnable, ActionListener> nextUnansweredEventListenerMap = new HashMap<>();
    private Map<Runnable, ActionListener> clearEventListenerMap = new HashMap<>();
    private Map<Runnable, ActionListener> randomizeEventListenerMap = new HashMap<>();

    public AnswersControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        clearButton = new JButton("Clear Answer");
        nextButton = new JButton("Next");
        nextUnansweredButton = new JButton("Next Unanswered");
        randomizeButton = new JButton("Randomize");
        add(clearButton);
        add(nextButton);
        add(nextUnansweredButton);
        add(randomizeButton);
    }

    public void addNextEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        nextEventListenerMap.put(listener, wrappedListener);
        nextButton.addActionListener(wrappedListener);
    }

    public void removeNextEventListener(Runnable listener) {
        ActionListener wrappedListener = nextEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            nextButton.removeActionListener(wrappedListener);
        }
    }

    public void addNextUnansweredEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        nextUnansweredEventListenerMap.put(listener, wrappedListener);
        nextUnansweredButton.addActionListener(wrappedListener);
    }

    public void removeNextUnansweredEventListener(Runnable listener) {
        ActionListener wrappedListener = nextUnansweredEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            nextUnansweredButton.removeActionListener(wrappedListener);
        }
    }

    public void addClearEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        clearEventListenerMap.put(listener, wrappedListener);
        clearButton.addActionListener(wrappedListener);
    }

    public void removeClearEventListener(Runnable listener) {
        ActionListener wrappedListener = clearEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            clearButton.removeActionListener(wrappedListener);
        }
    }

    public void addRandomizeEventListener(Runnable listener) {
        ActionListener wrappedListener = _ -> listener.run();
        randomizeEventListenerMap.put(listener, wrappedListener);
        randomizeButton.addActionListener(wrappedListener);
    }

    public void removeRandomizeEventListener(Runnable listener) {
        ActionListener wrappedListener = randomizeEventListenerMap.remove(listener);
        if (wrappedListener != null) {
            randomizeButton.removeActionListener(wrappedListener);
        }
    }
}

class WorkControlPanel extends JPanel {

    private AnswersChoicePanel answersPanel;
    private AnswersControlPanel answersControlPanel;

    public WorkControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel WorkControlPanel"));
        add(answersPanel = new AnswersChoicePanel());
        add(answersControlPanel = new AnswersControlPanel());
        answersControlPanel.addClearEventListener(answersPanel::clearSelection);
        answersControlPanel.addRandomizeEventListener(answersPanel::randomizeSelection);
    }

    public void setViewAnswersForQuestion(QuestionInWork questionInWork) {
        answersPanel.setQuestion(questionInWork);
    }

    public void addAnswersSelectedStateChangeListener(Consumer<QuestionInWork> listener) {
        answersPanel.addAnswersSelectedStateChangeListener(listener);
    }

    public void removeAnswersSelectedStateChangeListener(Consumer<QuestionInWork> listener) {
        answersPanel.removeAnswersSelectedStateChangeListener(listener);
    }

    public void addNextEventListener(Runnable listener) {
        answersControlPanel.addNextEventListener(listener);
    }

    public void addNextUnansweredEventListener(Runnable listener) {
        answersControlPanel.addNextUnansweredEventListener(listener);
    }

    public void removeNextEventListener(Runnable listener) {
        answersControlPanel.removeNextEventListener(listener);
    }

    public void removeNextUnansweredEventListener(Runnable listener) {
        answersControlPanel.removeNextUnansweredEventListener(listener);
    }

    public void addRandomizeEventListener(Runnable listener) {
        answersControlPanel.addRandomizeEventListener(listener);
    }

    public void removeRandomizeEventListener(Runnable listener) {
        answersControlPanel.removeRandomizeEventListener(listener);
    }
}

class QuestionViewPanel extends JPanel {

    private WebView webView;

    public QuestionViewPanel() {
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

    public void randomizeSelectedAnswers() {
        List<AnswerInWork> answers = getAnswers();
        answers.forEach(answer -> answer.setSelected(false));
        if (getQuestion().isMultipleChoices()) {
            // Randomly select a subset of answers
            answers.stream().filter(_ -> Math.random() > 0.5).forEach(answer -> answer.setSelected(true));
        } else {
            // Randomly select one answer
            int randomIndex = (int) (Math.random() * answers.size());
            answers.get(randomIndex).setSelected(true);
        }
        return;
    }

    public void deselectAnswer() {
        answers.forEach(answer -> answer.setSelected(false));
    }
}
