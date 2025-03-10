package DAL;

import java.sql.*;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/thitracnghiem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static int executeUpdate(String query, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ResultSet executeQuery(String query, Object... params) {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            setParameters(pstmt, params);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
}