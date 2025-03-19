package DAL;

import DTO.ResultDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResultDAL {
    
    public int getTotalResult() {
        int total = 0;
        ArrayList<ResultDTO> resultDTOs = new ArrayList<>();
        String query = "SELECT * FROM result";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                total ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public int getTotalResultAbove50() {
        int total = 0;
        String query = "SELECT * FROM result WHERE mark >= 50";

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                total++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public int getTotalResultBelow50() {
        int total = 0;
        String query = "SELECT * FROM result WHERE mark < 50";

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                total++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getTotalResultsByTestId(int testId) {
        int total = 0;
        String query = "SELECT * FROM result WHERE test_id = " + testId;  // Dùng query đơn giản không sử dụng PreparedStatement

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                total++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
    
    public int getTotalResultsByMonth(int i) {
        int total = 0;
        // Câu truy vấn để lọc theo tháng
        String query = "SELECT * FROM result WHERE MONTH(date) = " + i; // Lọc kết quả theo tháng

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                total++; // Tăng tổng số kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    
    public double getAverageMarkByMonth(int i) {
        double totalMarks = 0; // Tổng điểm của các kết quả trong tháng
        int totalResults = 0;  // Số lượng kết quả trong tháng

        // Câu truy vấn để lọc kết quả theo tháng
        String query = "SELECT * FROM result WHERE MONTH(date) = " + i; // Lọc kết quả theo tháng

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                double mark = rs.getDouble("mark"); // Lấy điểm của thí sinh
                totalMarks += mark; // Cộng dồn điểm vào tổng
                totalResults++; // Tăng số lượng kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tính toán điểm trung bình
        if (totalResults > 0) {
            return totalMarks / totalResults; // Trả về điểm trung bình
        } else {
            return 0; // Nếu không có kết quả nào, trả về 0
        }
    }

    
    public int getTotalResultsAbove50ByTestId(int testId) {
        int total = 0;
        // Cập nhật câu truy vấn để bao gồm cả điều kiện mark >= 50
        String query = "SELECT * FROM result WHERE test_id = " + testId + " AND mark >= 50";

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                total++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
    
    public int getTotalResultsBelow50ByTestId(int testId) {
        int total = 0;
        // Cập nhật câu truy vấn để bao gồm cả điều kiện mark >= 50
        String query = "SELECT * FROM result WHERE test_id = " + testId + " AND mark < 50";

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                total++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
    
    public double getMarkTBTestId(int testId) {
        double totalMark = 0;
        int count = 0;

        // Cập nhật câu truy vấn để lấy tất cả các giá trị mark của test_id
        String query = "SELECT mark FROM result WHERE test_id = " + testId;

        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                totalMark += rs.getDouble("mark"); // Cộng tổng điểm
                count++; // Đếm số lượng dòng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tính trung bình cộng
        if (count > 0) {
            double avgMark = totalMark / count;
            DecimalFormat df = new DecimalFormat("#.#");  // Định dạng số với một chữ số sau dấu thập phân
            return Double.parseDouble(df.format(avgMark));
        } else {
            return 0; // Trả về 0 nếu không có dữ liệu
        }
    }

    
}
