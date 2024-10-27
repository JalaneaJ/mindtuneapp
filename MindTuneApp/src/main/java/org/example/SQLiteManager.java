package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {

    // SQLite database URL
    private static final String URL = "jdbc:sqlite:focus_data.db";

    // Create SQLite database
    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                System.out.println("A new SQLite database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create sessions table
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS sessions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "focusDuration INTEGER," +
                "numDistractions INTEGER," +
                "mindfulnessLevel INTEGER," +
                "condition TEXT," +
                "task TEXT)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("The sessions table has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add session data to SQLite
    public static void addSessionToSQLite(int focusDuration, int numDistractions, int mindfulnessLevel, String condition, String task) {
        String sql = "INSERT INTO sessions(focusDuration, numDistractions, mindfulnessLevel, condition, task) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, focusDuration);
            pstmt.setInt(2, numDistractions);
            pstmt.setInt(3, mindfulnessLevel);
            pstmt.setString(4, condition);
            pstmt.setString(5, task);
            pstmt.executeUpdate();
            System.out.println("Session added to SQLite database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieve all session data from SQLite
    public static void getSessionsFromSQLite() {
        String sql = "SELECT * FROM sessions";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Focus Duration: " + rs.getInt("focusDuration"));
                System.out.println("Number of Distractions: " + rs.getInt("numDistractions"));
                System.out.println("Mindfulness Level: " + rs.getInt("mindfulnessLevel"));
                System.out.println("Condition: " + rs.getString("condition"));
                System.out.println("Task: " + rs.getString("task"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
