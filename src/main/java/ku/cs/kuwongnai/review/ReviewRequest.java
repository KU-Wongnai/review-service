package ku.cs.kuwongnai.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequest {

  @NotNull(message = "The user id is required.")
  private Long userId;

  @NotBlank(message = "The title is required.")
  private String title;

  @NotBlank(message = "The title is required.")
  private String content;

  @Min(0)
  @Max(5)
  private double rating;
}
