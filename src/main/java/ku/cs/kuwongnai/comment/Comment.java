package ku.cs.kuwongnai.comment;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import ku.cs.kuwongnai.review.Review;
import ku.cs.kuwongnai.user.User;
import lombok.Data;

@Entity
@Data
public class Comment {

  @Id
  @GeneratedValue
  private Long id;

  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  // @JsonIgnore
  private User user;

  @ManyToOne
  @JoinColumn(name = "review_id")
  @JsonIgnore
  private Review review;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private LocalDateTime updatedAt;
}
