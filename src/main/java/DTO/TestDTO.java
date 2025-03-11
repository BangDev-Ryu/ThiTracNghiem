package DTO;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private int id;
    private String testCode;
    private String name;
    private int time;
    private int limit;
    private Timestamp date; 
    private int status;
}
