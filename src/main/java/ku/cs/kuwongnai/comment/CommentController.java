package ku.cs.kuwongnai.comment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping
  public Comment create(@Valid @RequestBody CommentRequest comment) {
    return commentService.create(comment);
  }

  @PutMapping("/{commentId}")
  public Comment update(@PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest comment) {
    return commentService.update(commentId, comment);
  }

  @DeleteMapping("/{commentId}")
  public Comment delete(@PathVariable Long commentId) {
    return commentService.delete(commentId);
  }

}
