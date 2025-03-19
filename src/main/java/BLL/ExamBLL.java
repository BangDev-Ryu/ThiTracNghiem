package BLL;

import DAL.ExamDAL;
import DTO.ExamDTO;
import java.util.ArrayList;

public class ExamBLL {
    private final ExamDAL examDAL = new ExamDAL();
    
    public int getTotalExam() {
        return examDAL.getTotalExam();
    }
    
    public ArrayList<ExamDTO> getAllExams() {
        return examDAL.getAllExams();
    }

}
