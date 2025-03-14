package DAL;

import DTO.TestDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestDAL {
    
    public ArrayList<TestDTO> getAllTests() {
        ArrayList<TestDTO> testDTOs = new ArrayList<>();
        String query = "SELECT * FROM test";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                testDTOs.add(new TestDTO(
                    rs.getInt("id"),
                    rs.getString("test_code"),
                    rs.getString("name"),
                    rs.getInt("time"),
                    rs.getInt("limit"),
                    rs.getTimestamp("date"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testDTOs;
    }
    
    public boolean addTest(TestDTO testDTO) {
        String query = "INSERT INTO test (test_code, name, time, limit, date, status) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
        return ConnectDB.executeUpdate(
                query, testDTO.getTestCode(), testDTO.getName(), testDTO.getTime(), testDTO.getLimit(), testDTO.getDate(), testDTO.isStatus()) > 0;
    }

    public boolean updateTest(TestDTO testDTO) {
        String query = "UPDATE test SET test_code = ?, name = ?, time = ?, limit = ?, date = ?, status = ? WHERE id = ?";
        return ConnectDB.executeUpdate(query, testDTO.getTestCode(), testDTO.getName(), testDTO.getTime(), testDTO.getLimit(), testDTO.getDate(), testDTO.isStatus(), testDTO.getId()) > 0;
    }

    public boolean deleteTest(int testId) {
        String query = "DELETE FROM test WHERE id = ?";
        return ConnectDB.executeUpdate(query, testId) > 0;
    }
}
