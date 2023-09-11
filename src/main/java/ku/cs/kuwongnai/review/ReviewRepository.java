package ku.cs.kuwongnai.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.restaurant.id = :restaurantId")
  Review findByUserIdAndRestaurantId(Long userId, Long restaurantId);
}
