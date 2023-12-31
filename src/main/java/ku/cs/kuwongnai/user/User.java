package ku.cs.kuwongnai.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import ku.cs.kuwongnai.comment.Comment;
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

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Review> reviews = new ArrayList<>();

  @ManyToMany(mappedBy = "likes", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Review> likedReviews = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();
}
