package ku.cs.kuwongnai.user;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import ku.cs.kuwongnai.review.Review;
import lombok.Data;

@Entity
@Data
public class User {

  @Id
  private Long id;
  private String name;
  private String email;
  private LocalDateTime emailVerifiedAt;
  private String avatar;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Review> reviews;
}
