package DAL;

import DTO.ExamDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamDAL {
    
    public int getTotalExam() {
        int total = 0;
        ArrayList<ExamDTO> examDTOs = new ArrayList<>();
        String query = "SELECT * FROM exam";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                examDTOs.add(new ExamDTO(
                    rs.getInt("test_id"),
                    rs.getString("test_code"),
                    rs.getString("ex_order"),
                    rs.getString("ex_code")
                ));
                total ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public ArrayList<ExamDTO> getAllExams() {
        ArrayList<ExamDTO> examDTOs = new ArrayList<>();
        String query = "SELECT * FROM exam";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                examDTOs.add(new ExamDTO(
                    rs.getInt("test_id"),
                    rs.getString("test_code"),
                    rs.getString("ex_order"),
                    rs.getString("ex_code")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examDTOs;
    }
    
}
