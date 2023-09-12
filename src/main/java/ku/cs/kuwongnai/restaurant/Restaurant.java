package ku.cs.kuwongnai.restaurant;

import java.util.List;

import jakarta.persistence.CascadeType;
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

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews;
}
