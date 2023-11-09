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

public class TypingController implements KeyListener {
    private Typing typing;
    private ThreadCountdown thread;
    private List<String> records;
    private Random random;

    public TypingController(Typing typing) {
        this.typing = typing;
        this.records = new ArrayList<>();
        this.random = new Random();
        this.typing.addKeyListener(this);
        this.typing.setFocusable(true);
        this.readCSV("src\\Assets\\dataset.csv");
    }

    public void readCSV(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values[0]); // Only add the first column
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getWord() {
        ArrayList<JLabel> labels = new ArrayList();
        for (int i = 0; i < 200; i++) {
            JLabel label = new JLabel();
            label.setFont(new java.awt.Font("Segoe UI", 0, 28));
            label.setText(records.get(random.nextInt(records.size())));
            labels.add(label);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (thread == null || !thread.isAlive()) {
            thread = new ThreadCountdown(59, typing);
            thread.start();
        }
        System.out.print(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void interrupt() {
        thread.interrupt();
        this.typing.requestFocus();
    }
}