package ku.cs.kuwongnai.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

  @NotNull(message = "User id is required.")
  public Long userId;

  @NotNull(message = "Review id is required.")
  public Long reviewId;

  @NotBlank(message = "The content is required.")
  public String content;
}
