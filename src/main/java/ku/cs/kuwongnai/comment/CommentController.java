package ku.cs.kuwongnai.comment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("/reviews/{reviewId}/comments")
  public Comment create(@PathVariable Long reviewId, @Valid @RequestBody CommentRequest comment,
      @AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return commentService.create(comment, reviewId, Long.parseLong(userId));
  }

  @PutMapping("/comments/{commentId}")
  public Comment update(@PathVariable Long commentId, @Valid @RequestBody CommentRequest comment,
      @AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return commentService.update(commentId, comment, Long.parseLong(userId));
  }

  @DeleteMapping("/comments/{commentId}")
  public Comment delete(@PathVariable Long commentId, @AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return commentService.delete(commentId, Long.parseLong(userId));
  }

}
