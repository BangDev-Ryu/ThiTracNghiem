package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private int id;
    private int questionId;
    private String content;
    private String picture;
    private boolean isRight;
    private boolean status;
}
