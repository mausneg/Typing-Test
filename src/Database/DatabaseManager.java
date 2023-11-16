package Database;

import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.util.Base64;

public class DatabaseManager {
    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;

    public DatabaseManager() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/typingtestdb", "root", "");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String hashedPassword = Base64.getEncoder().encodeToString(digest);
            return hashedPassword;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean register(String username, String password) {
        try {
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            String hashedPassword = hashPassword(password);
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return false;
    }

    public boolean login(String username, String password) {
        try {
            String hashedPassword = hashPassword(password);
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return false;
    }

    public void insertScore(int id, int score) {
        try {
            String query = "INSERT INTO scores (id_user, score) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public int getId(String username) {
        try {
            String query = "SELECT id_user FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return 0;
    }

    public String getUsername(int id) {
        try {
            String query = "SELECT username FROM users WHERE id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return "";
    }

    public int getHighscore(int id) {
        try {
            String query = "SELECT score FROM scores WHERE id_user = ? order by score desc limit 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return 0;
    }

    public int getJumlahTes(int id) {
        try {
            String query = "SELECT count(*) from scores where id_user = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return 0;
    }

    public ArrayList<String> getLeaderboard() {
        ArrayList<String> listLead = new ArrayList<String>();
        String temp = "";
        try {
            String query = "SELECT users.username, MAX(scores.score) AS high_score FROM users INNER JOIN scores ON users.id_user = scores.id_user GROUP BY users.username ORDER BY high_score DESC LIMIT 5";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                temp = resultSet.getString(1) + " " + resultSet.getString(2);
                listLead.add(temp);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listLead;
    }

}
