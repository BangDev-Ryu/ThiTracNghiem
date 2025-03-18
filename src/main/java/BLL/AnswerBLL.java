package BLL;

import DAL.AnswerDAL;
import DTO.AnswerDTO;
import java.util.ArrayList;

public class AnswerBLL {
    private final AnswerDAL answerDAL = new AnswerDAL();
    
    public ArrayList<AnswerDTO> getAllAnswers() {
        return answerDAL.getAllAnswers();
    }
    
    public AnswerDTO getAnswerById(int id) {
        return answerDAL.getAnswerById(id);
    }
    
    public ArrayList<AnswerDTO> getAnswersByQuestionId(int id) {
        return answerDAL.getAnswersByQuestionId(id);
    }

    public boolean addAnswer(AnswerDTO a) {
        return answerDAL.addAnswer(a);
    }

    public boolean updateAnswer(AnswerDTO a) {
        return answerDAL.updateAnswer(a);
    }

    public boolean deleteAnswer(int id) {
        return answerDAL.deleteAnswer(id);
    } 
}
