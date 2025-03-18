package DAL;

import DTO.AnswerDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDAL {
    
    public ArrayList<AnswerDTO> getAllAnswers() {
        ArrayList<AnswerDTO> answerDTOs = new ArrayList<>();
        String query = "SELECT * FROM answer";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                answerDTOs.add(new AnswerDTO(
                    rs.getInt("id"),
                    rs.getInt("question_id"),
                    rs.getString("content"),
                    rs.getString("picture"),
                    rs.getBoolean("is_right"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answerDTOs;
    }
    
    public AnswerDTO getAnswerById(int id) {
        String query = "SELECT * FROM answer WHERE id = ?";
        try (ResultSet rs = ConnectDB.executeQuery(query, id)) {
            if (rs.next()) {
                return new AnswerDTO(
                    rs.getInt("id"),
                    rs.getInt("question_id"),
                    rs.getString("content"),
                    rs.getString("picture"),
                    rs.getBoolean("is_right"),
                    rs.getBoolean("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<AnswerDTO> getAnswersByQuestionId(int id) {
        ArrayList<AnswerDTO> answerDTOs = new ArrayList<>();
        String query = "SELECT * FROM answer WHERE question_id = ?";
        
        try (ResultSet rs = ConnectDB.executeQuery(query, id)) {
            while (rs.next()) {
                answerDTOs.add(new AnswerDTO(
                    rs.getInt("id"),
                    rs.getInt("question_id"),
                    rs.getString("content"),
                    rs.getString("picture"),
                    rs.getBoolean("is_right"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answerDTOs;
    }
    
    public boolean addAnswer(AnswerDTO a) {
        String query = "INSERT INTO answer (question_id, content, picture, is_right, status) "
                        + "VALUES (?, ?, ?, ?, ?)";
        return ConnectDB.executeUpdate(
                query, a.getQuestionId(), a.getContent(), a.getPicture(), a.isRight(), a.isStatus()) > 0;
    }
    
    public boolean updateAnswer(AnswerDTO a) {
        String query = "UPDATE answer SET question_id = ?, content = ?, picture = ?, is_right = ?, status = ? WHERE id = ?";
        return ConnectDB.executeUpdate(
                query, a.getQuestionId(), a.getContent(), a.getPicture(), a.isRight(), a.isStatus(), a.getId()) > 0;
    }
    
    public boolean deleteAnswer(int id) {
        String query = "DELETE FROM answer WHERE id = ?";
        return ConnectDB.executeUpdate(query, id) > 0;
    }
}
