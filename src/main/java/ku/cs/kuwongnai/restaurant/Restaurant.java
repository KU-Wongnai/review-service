package ku.cs.kuwongnai.restaurant;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import ku.cs.kuwongnai.review.Review;
import lombok.Data;

@Entity
@Data
public class Restaurant {

  @Id
  private Long id;

  @OneToMany(mappedBy = "restaurant")
  private List<Review> reviews;
}
