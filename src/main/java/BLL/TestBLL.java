package BLL;

import DAL.TestDAL;
import DTO.TestDTO;
import java.util.ArrayList;

public class TestBLL {
    private final TestDAL testDAL = new TestDAL();

    public ArrayList<TestDTO> getAllTests() {
        return testDAL.getAllTests();
    }
    

    public boolean addTest(TestDTO testDTO) {
        return testDAL.addTest(testDTO);
    }

    public boolean updateTest(TestDTO testDTO) {
        return testDAL.updateTest(testDTO);
    }

    public boolean deleteTest(int id) {
        return testDAL.deleteTest(id);
    }
}
