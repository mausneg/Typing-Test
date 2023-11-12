package Controller;

import java.util.ArrayList;

import javax.swing.JLabel;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import Database.DatabaseManager;
import Views.Leaderboard;

public class LeaderboardController {
    private Leaderboard leaderboard;
    private DatabaseManager databaseManager;
    private ArrayList<String> listLead;

    public LeaderboardController(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        this.databaseManager = new DatabaseManager();
        this.listLead = this.databaseManager.getLeaderboard();
        this.display();
    }

    public void display() {
        for (int i = 0; i < listLead.size(); i++) {
            JLabel label = new JLabel("\t" + String.valueOf(i + 1) + ". " + listLead.get(i) + " KPM");
            label.setFont(new java.awt.Font("Segoe UI", 0, 18));
            label.setForeground(new java.awt.Color(71, 85, 105));
            leaderboard.setLead(label);
        }
    }

}
