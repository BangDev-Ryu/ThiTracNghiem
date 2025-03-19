package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {

    public TopicDTO(String name, int parent, boolean status) {
        this.name = name;
        this.parent = parent;
        this.status = status;
    }
    private int id;
    private String name;
    private int parent; 
    private boolean status;
}
