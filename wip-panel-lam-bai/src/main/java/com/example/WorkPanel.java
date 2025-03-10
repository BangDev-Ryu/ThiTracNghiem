package com.example;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WorkPanel extends JPanel {

    private WorkAreaPanel workAreaPanel;

    public WorkPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new WorkInfoPanel());
        add(workAreaPanel = new WorkAreaPanel());
        add(new WorkControlPanel());

        // TODO: tam thoi view question 1 de test truoc
        var qList = Util.readCsvToQuestions("./questions.csv", true);
        var tTest = new TheTest();
        tTest.setQuestions(qList);
        initializeTheTest(tTest);
    }

    public void initializeTheTest(TheTest theTest) {
        // TODO: tam thoi view question 1 de test truoc
        workAreaPanel.setViewQuestion(theTest.getQuestions().get(0));
    }
}

class WorkInfoPanel extends JPanel {

    public WorkInfoPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel 1"));
    }
}

class WorkAreaPanel extends JPanel {

    private WebViewPanel webViewPanel;

    public WorkAreaPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel 2"));
        add(webViewPanel = new WebViewPanel());
    }

    public void setViewQuestion(Question question) {
        webViewPanel.setViewContent(Util.generateQuestionHtml(question));
    }
}

class WorkControlPanel extends JPanel {

    public WorkControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Panel 3"));
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
