package ku.cs.kuwongnai.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

  // Deprecated used jwt to get user id instead
  // @NotNull(message = "User id is required.")
  // public Long userId;

  // Deprecated get from path variable instead
  // @NotNull(message = "Review id is required.")
  // public Long reviewId;

  @NotBlank(message = "The content is required.")
  @Size(min = 1, max = 255)
  public String content;
}
