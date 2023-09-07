package ku.cs.kuwongnai.comment;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentUpdateRequest {

  @Size(min = 1, max = 255)
  private String content;
}
