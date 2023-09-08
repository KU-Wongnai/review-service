package ku.cs.kuwongnai.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequest {

  // Deprecated used jwt to get user id instead
  // @NotNull(message = "The user id is required.")
  // private Long userId;

  @NotBlank(message = "The title is required.")
  private String title;

  @NotBlank(message = "The title is required.")
  private String content;

  @Min(0)
  @Max(5)
  private double rating;
}
