package Database;

import java.sql.*;
import Controller.RegisterController;

public class DatabaseManager {
    private RegisterController registerController;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DatabaseManager(RegisterController registerController) {
        this.registerController = registerController;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/typingtestdb", "root", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void register(String username, String password) {
        try {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
