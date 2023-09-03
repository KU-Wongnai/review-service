package ku.cs.kuwongnai.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequest {
  @NotBlank(message = "The title is required.")
  private String title;

  @NotBlank(message = "The title is required.")
  private String content;

  // @NotBlank(message = "The title is required.")
  @Min(0)
  @Max(5)
  private double rating;
}
