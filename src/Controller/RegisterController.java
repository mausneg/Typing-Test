package Controller;

import Database.DatabaseManager;
import Views.Register;

public class RegisterController {
    private String username;
    private String password;
    private Register register;
    private DatabaseManager dbManager;

    public RegisterController(Register register) {
        this.register = register;
        this.dbManager = new DatabaseManager(this);
        
    }
    public void register(String username, String password) {
        this.username = username;
        this.password = password;
        dbManager.register(username, password);
    }
}
