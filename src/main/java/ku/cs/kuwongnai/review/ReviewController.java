package ku.cs.kuwongnai.review;

import java.util.List;
import java.util.Map;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @GetMapping("/hello")
  public String hello(@AuthenticationPrincipal Jwt jwt) {
    Map<String, Object> claims = jwt.getClaims();

    String userId = (String) claims.get("sub");

    return "Hello user " + userId + "!";
  }

  @GetMapping
  public List<Review> findAll() {
    return reviewService.findAll();
  }

  @GetMapping("/{id}")
  public Review findById(@PathVariable Long id) {
    return reviewService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Review create(@Valid @RequestBody ReviewRequest review) {
    return reviewService.create(review);
    // return new ResponseEntity<Review>(reviewService.create(review),
    // HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public Review updateById(@PathVariable Long id, @Valid @RequestBody ReviewRequest review) {
    return reviewService.updateById(id, review);
  }

  @DeleteMapping("/{id}")
  public Review deleteById(@PathVariable Long id) {
    return reviewService.deleteById(id);
  }

  @PostMapping("/{id}/like/{userId}")
  public Review likeById(@PathVariable Long id, @PathVariable Long userId) {
    return reviewService.likeById(id, userId);
  }

}
