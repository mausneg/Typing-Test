package Controller;

import Views.Typing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Database.DatabaseManager;

public class TypingController {
    private Typing typing;
    private ThreadCountdown threadCountdown;
    private ThreadWord threadWord;
    private List<String> records;
    private ArrayList<JPanel> panels;
    private Random random;
    private JPanel currentPanel;
    private JLabel currentLabel;
    private int labelIndex;
    private JPanel container;
    private int score;
    private int id;
    private DatabaseManager dbManager;

    public TypingController(int id, Typing typing) {
        this.typing = typing;
        this.dbManager = new DatabaseManager();
        this.id = id;
        this.score = 0;
        container = typing.getJPanel2();
        this.labelIndex = 0;
        this.records = new ArrayList<>();
        this.random = new Random();
        this.typing.setFocusable(true);
        this.readCSV("src\\Assets\\dataset.csv");
        this.threadWord = new ThreadWord(typing, this);
        this.threadWord.start();

    }

    public void readCSV(String pathCSV) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathCSV));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getWord() {
        ArrayList<JLabel> labels = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            JLabel label = new JLabel();
            label.setFont(new java.awt.Font("Segoe UI", 0, 28));
            label.setForeground(new java.awt.Color(71, 85, 105));
            label.setText(records.get(random.nextInt(records.size())));
            labels.add(label);
        }
        panels = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(new java.awt.Color(255, 255, 255));
            panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 0));
            for (int j = 0; j < 5; j++) {
                panel.add(labels.get(0));
                labels.remove(0);
            }
            panels.add(panel);
        }
        for (int i = 0; i < 2; i++) {
            typing.addPanelText(panels.get(i));
            panels.remove(i);
        }

    }

    public void checkWord(String text) {
        if (text != null && !text.isEmpty() && (this.threadCountdown == null || !this.threadCountdown.isAlive())) {
            this.threadCountdown = new ThreadCountdown(59, typing, this);
            this.threadCountdown.start();
        }

        currentPanel = (JPanel) container.getComponent(0);
        currentLabel = (JLabel) currentPanel.getComponent(labelIndex);
        int componentCount = currentPanel.getComponentCount();
        if (text.trim().equals(currentLabel.getText())) {
            currentLabel.setForeground(new java.awt.Color(34, 139, 34));
            labelIndex++;
            score++;
            typing.setJTextField1("");
            if (labelIndex == componentCount) {
                container.remove(0);
                container.add(panels.get(0));
                panels.remove(0);
                labelIndex = 0;
                container.revalidate();
                container.repaint();
            }
        } else {
            currentLabel.setForeground(new java.awt.Color(255, 0, 0));
        }
    }

    public void restart() {
        this.labelIndex = 0;
        typing.setJLabel2(String.valueOf(0));
        try {
            this.threadCountdown.interrupt();
            this.threadWord.interrupt();
        } catch (Exception e) {
        }
        panels.clear();
        this.typing.cleanPanelText();
        this.getWord();
        typing.setJTextField1("");
        this.threadWord = new ThreadWord(typing, this);
        this.threadWord.start();
    }

    public void end() {
        try {
            this.threadCountdown.interrupt();
            this.threadWord.interrupt();
        } catch (Exception e) {
        }
        this.typing.cleanPanelText();
        typing.setJTextField1("");
        typing.setJLabel1("00:00");
        typing.setJLabel2(String.valueOf(score));
        this.dbManager.insertScore(id, score);
    }
}