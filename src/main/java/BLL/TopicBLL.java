package BLL;

import DAL.TopicDAL;
import DTO.TopicDTO;
import java.util.ArrayList;

public class TopicBLL {
    private final TopicDAL topicDAL = new TopicDAL();

    public TopicDTO getTopicById(int id) {
        return topicDAL.getTopicById(id);
    }
    public int getLastTopicId(){
        return topicDAL.getLastTopicId();
    }
    public ArrayList<TopicDTO> getAllTopics() {
        return topicDAL.getAllTopics();
    }
    
    public ArrayList<String> getAllParents() {
        return topicDAL.getAllParents();
    }

    public boolean addTopic(TopicDTO topicDTO) {
        return topicDAL.addTopic(topicDTO);
    }

    public boolean updateTopic(TopicDTO topicDTO) {
        return topicDAL.updateTopic(topicDTO);
    }

    public boolean deleteTopic(int id) {
        return topicDAL.deleteTopic(id);
    }
    


}
