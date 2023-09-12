package ku.cs.kuwongnai.review;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ku.cs.kuwongnai.comment.Comment;
import ku.cs.kuwongnai.image.Image;
import ku.cs.kuwongnai.restaurant.Restaurant;
import ku.cs.kuwongnai.user.User;
import lombok.Data;

@Entity
@Data
public class Review {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  @JsonIgnore
  private Restaurant restaurant;

  @NotNull
  private String title;

  @NotNull
  @NotBlank(message = "The content is required.")
  private String content;

  @Min(0)
  @Max(5)
  @NotNull
  private double rating;

  @ManyToMany
  @JoinTable(name = "review_like", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> likes;

  @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private LocalDateTime updatedAt;

}
