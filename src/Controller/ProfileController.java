package Controller;

import Database.DatabaseManager;
import Views.Profile;

public class ProfileController {
    private Profile profile;
    private int id;
    private String username;
    private int highscore;
    private int jumlahTes;
    private DatabaseManager databaseManager;

    public ProfileController(int id, Profile profile) {
        this.profile = profile;
        databaseManager = new DatabaseManager();
        this.id = id;
        this.username = databaseManager.getUsername(id);
        this.highscore = databaseManager.getHighscore(id);
        this.jumlahTes = databaseManager.getJumlahTes(id);
        this.profile.setProfile(username, highscore, jumlahTes);

    }
}
