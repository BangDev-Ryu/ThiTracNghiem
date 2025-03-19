package BLL;

import DTO.UserDTO;
import DAL.UserDAL;
import java.util.ArrayList;

public class UserBLL {
    private final UserDAL userDAL = new UserDAL();
    
    public ArrayList<UserDTO> getAllUsers() {
        return userDAL.getAllUsers();
    }
    
    public int getAllUsersNotIsAdmin() {
        return userDAL.getAllUsersNotIsAdmin();
    }
    
    public UserDTO getUserById(int id) {
        return userDAL.getUserById(id);
    }
    
    public boolean addUser(UserDTO a) {
        return userDAL.addUser(a);
    }
     
    public boolean updateUser(UserDTO a) {
        return userDAL.updateUser(a);
    }
    
    public boolean deleteUser(int id) {
        return userDAL.deleteUser(id);
    }
    
    public ArrayList<UserDTO> searchUsers(String keyword) {
        return userDAL.searchUsers(keyword);
    }
}
