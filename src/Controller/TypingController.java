package Controller;

import Views.Typing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TypingController implements KeyListener {
    private Typing typing;
    private ThreadCountdown threadCountdown;
    private ThreadWord threadWord;
    private List<String> records;
    private ArrayList<JPanel> panels;
    private Random random;

    public TypingController(Typing typing) {
        this.typing = typing;
        this.records = new ArrayList<>();
        this.random = new Random();
        this.typing.addKeyListener(this);
        this.typing.setFocusable(true);
        this.readCSV("src\\Assets\\dataset.csv");
        this.threadWord = new ThreadWord(typing, this);
        this.threadWord.start();
    }

    public void readCSV(String csvFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
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
        ArrayList<JLabel> labels = new ArrayList();
        for (int i = 0; i < 240; i++) {
            JLabel label = new JLabel();
            label.setFont(new java.awt.Font("Segoe UI", 0, 28));
            label.setText(records.get(random.nextInt(records.size())));
            labels.add(label);
        }
        panels = new ArrayList();
        for (int i = 0; i < 40; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(new java.awt.Color(255, 255, 255));
            panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 0));
            for (int j = 0; j < 6; j++) {
                panel.add(labels.get(i * 6 + j));
            }
            panels.add(panel);
        }
        for (int i = 0; i < 2; i++) {
            typing.addPanelText(panels.get(i));
            panels.remove(i);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // if (this.threadCountdown == null || !this.threadCountdown.isAlive()) {
        // this.threadCountdown = new ThreadController(1,59, typing, this);
        // }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void checkWord(String text) {
        if (text != null && !text.isEmpty() && this.threadCountdown == null) {
            this.threadCountdown = new ThreadCountdown(59, typing, this);
            this.threadCountdown.start();
        }

    }

    public void interrupt() {
        try {
            this.threadCountdown.interrupt();
        } catch (Exception e) {
        }
        this.typing.requestFocus();
        this.typing.cleanPanelText();
        typing.setJTextField1("");
        this.getWord();
    }
}