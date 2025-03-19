/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.TopicDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Minh
 */
public class TopicDAL {
    
    public int getLastTopicId() {
        int lastTopicId = -1; 
        String query = "SELECT id FROM topic ORDER BY id DESC LIMIT 1";
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            if (rs.next()) {
                lastTopicId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastTopicId;
    }
    
    public TopicDTO getTopicById(int id) {
        String query = "SELECT * FROM topic WHERE id = ?";
        try (ResultSet rs = ConnectDB.executeQuery(query, id)) {
            if (rs.next()) {
                return new TopicDTO(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("parent"),
                    rs.getBoolean("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<TopicDTO> getAllTopics() {
        ArrayList<TopicDTO> topicDTOs = new ArrayList<>();
        String query = "SELECT * FROM topic WHERE status = 1";
        
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                topicDTOs.add(new TopicDTO(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("parent"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topicDTOs;
    }
    
    public ArrayList<String> getAllParents() {
        ArrayList<String> parentIds = new ArrayList<>();
        String query = "SELECT DISTINCT id, name FROM topic WHERE id IS NOT NULL";

        parentIds.add("0");
        try (ResultSet rs = ConnectDB.executeQuery(query)) {
            while (rs.next()) {
                parentIds.add(rs.getInt("id") +  "-" + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parentIds;
    }

    
    public boolean addTopic(TopicDTO topicDTO) {
        String query = "INSERT INTO topic (name, parent,status) VALUES (?, ?, ?)";
        return ConnectDB.executeUpdate(
                query, topicDTO.getName(), topicDTO.getParent(), topicDTO.isStatus()) > 0;
    }

    public boolean updateTopic(TopicDTO topicDTO) {
        String query = "UPDATE topic SET name = ?, parent = ? WHERE id = ?";
        return ConnectDB.executeUpdate(query, topicDTO.getName(), topicDTO.getParent(), topicDTO.getId()) > 0;
    }

    
    public boolean deleteTopic(int testId) {
        String query = "UPDATE topic SET status = 0 WHERE id = ?";
        return ConnectDB.executeUpdate(query, testId) > 0;
    }
}
