package Controller;

import Database.DatabaseManager;
import Views.Login;

public class LoginController {
    private String username;
    private String password;
    private Login login;
    private DatabaseManager dbManager;

    public LoginController(Login login) {
        this.login = login;
        this.dbManager = new DatabaseManager();

    }

    public boolean login(String username, String password) {
        this.username = username;
        this.password = password;
        return dbManager.login(username, password);
    }
    public int getId(String username) {
        return dbManager.getId(username);
    }
}
