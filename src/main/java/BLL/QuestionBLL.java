package BLL;

import DAL.QuestionDAL;
import DTO.QuestionDTO;
import java.util.ArrayList;

public class QuestionBLL {
    private final QuestionDAL questionDAL = new QuestionDAL();
    
    public ArrayList<QuestionDTO> getAllQuestions() {
        return questionDAL.getAllQuestions();
    }

    public boolean addQuestion(QuestionDTO q) {
        return questionDAL.addQuestion(q);
    }

    public boolean updateQuestion(QuestionDTO q) {
        return questionDAL.updateQuestion(q);
    }

    public boolean deleteQuestion(int id) {
        return questionDAL.deleteQuestion(id);
    } 
}
