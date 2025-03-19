package DTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResultDTO {
    private int id;
    private int userId;
    private int testId;
    private int mark;
    private Date date;
}
