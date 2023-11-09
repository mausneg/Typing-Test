package Controller;

import Views.Typing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class TypingController {
    private Typing typing;
    private List<String> records;
    private Random random;

    public TypingController(Typing typing) {
        this.typing = typing;
        this.records = new ArrayList<>();
        this.random = new Random();
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
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel();
            label.setFont(new java.awt.Font("Segoe UI", 0, 28));
            label.setText(records.get(random.nextInt(records.size())));
            labels.add(label);
        }

    }

}