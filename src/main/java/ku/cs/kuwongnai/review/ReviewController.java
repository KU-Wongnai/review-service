package ku.cs.kuwongnai.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @GetMapping("/reviews")
  public List<Review> findAll() {
    return reviewService.findAll();
  }

  @GetMapping("/reviews/{id}")
  public Review findById(@PathVariable Long id) {
    return reviewService.findById(id);
  }

  @GetMapping("/restaurant/{restaurantId}/reviews")
  public List<Review> findAllFromRestaurant(@PathVariable Long restaurantId) {
    return reviewService.findAllFromRestaurant(restaurantId);
  }

  @PostMapping("/restaurant/{restaurantId}/reviews")
  @ResponseStatus(HttpStatus.CREATED)
  public Review create(@PathVariable Long restaurantId, @Valid @RequestBody ReviewRequest review,
      @AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return reviewService.create(review, Long.parseLong(userId), restaurantId);
  }

  @PutMapping("/reviews/{id}")
  public Review updateById(@PathVariable Long id, @Valid @RequestBody ReviewRequest review,
      @AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return reviewService.updateById(id, review, Long.parseLong(userId));
  }

  @DeleteMapping("/reviews/{id}")
  public Review deleteById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return reviewService.deleteById(id, Long.parseLong(userId));
  }

  @PostMapping("/reviews/{id}/like")
  public Review likeById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return reviewService.likeById(id, Long.parseLong(userId));
  }

}
