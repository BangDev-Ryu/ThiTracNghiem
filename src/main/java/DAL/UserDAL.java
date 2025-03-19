package DAL;

import DTO.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAL {
    public ArrayList<UserDTO> getAllUsers() {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        String query = "SELECT * FROM user";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                userDTOs.add(new UserDTO(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getBoolean("is_admin")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDTOs;
    }
    
    public UserDTO getUserById(int id) {
        String query = "SELECT * FROM user WHERE id = ?";
        try (ResultSet rs = ConnectDB.executeQuery(query, id)) {
            if (rs.next()) {
                return new UserDTO(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<UserDTO> searchUsers(String keyword) {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        String query = "SELECT * FROM user WHERE id LIKE ? OR name LIKE ?";

        try (ResultSet rs = ConnectDB.executeQuery(query, "%" + keyword + "%", "%" + keyword + "%")) {
            while (rs.next()) {
                userDTOs.add(new UserDTO(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getBoolean("is_admin")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDTOs;
    }
    
    public boolean addUser(UserDTO user) {
        String query = "INSERT INTO user (name, email, password, fullname, is_admin) "
                        + "VALUES (?, ?, ?, ?, ?)";
        return ConnectDB.executeUpdate(
                query, user.getName(), user.getEmail(), user.getPassword(), user.getFullname(), user.isAdmin()) > 0;
    }
    
    public boolean updateUser(UserDTO user) {
        String query = "UPDATE user SET name = ?, email = ?, password = ?, fullname = ?, is_admin = ? WHERE id = ?";
        return ConnectDB.executeUpdate(
                query, user.getName(), user.getEmail(), user.getPassword(), user.getFullname(), user.isAdmin(), user.getId()) > 0;
    }
    
    public boolean deleteUser(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        return ConnectDB.executeUpdate(query, id) > 0;
    }
}
