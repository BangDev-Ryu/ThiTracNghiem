package DAL;

import DTO.QuestionDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionDAL {
    
    public ArrayList<QuestionDTO> getAllQuestions() {
        ArrayList<QuestionDTO> questionDTOs = new ArrayList<>();
        String query = "SELECT * FROM question";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                questionDTOs.add(new QuestionDTO(
                    rs.getInt("id"),
                    rs.getInt("topic_id"),
                    rs.getString("content"),
                    rs.getString("picture"),
                    rs.getString("level"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionDTOs;
    }
    
    public boolean addQuestion(QuestionDTO q) {
        String query = "INSERT INTO question (topic_id, content, picture, level, status) "
                        + "VALUES (?, ?, ?, ?, ?)";
        return ConnectDB.executeUpdate(
                query, q.getTopicId(), q.getContent(), q.getPicture(), q.getLevel(), q.isStatus()) > 0;
    }

    public boolean updateQuestion(QuestionDTO q) {
        String query = "UPDATE question SET topic_id = ?, content = ?, picture = ?, level = ?, status = ? WHERE id = ?";
        return ConnectDB.executeUpdate(
                query, q.getTopicId(), q.getContent(), q.getPicture(), q.getLevel(), q.isStatus(), q.getId()) > 0;
    }

    public boolean deleteQuestion(int id) {
        String query = "DELETE FROM question WHERE id = ?";
        return ConnectDB.executeUpdate(query, id) > 0;
    }
}
