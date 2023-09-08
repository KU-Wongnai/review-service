package ku.cs.kuwongnai.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.review.Review;
import ku.cs.kuwongnai.review.ReviewRepository;
import ku.cs.kuwongnai.user.User;
import ku.cs.kuwongnai.user.UserRepository;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private UserRepository userRepository;

  public Comment create(CommentRequest comment, Long reviewId, Long userId) {
    Comment record = new Comment();
    record.setContent(comment.getContent());

    Review review = reviewRepository.findById(reviewId).orElse(null);

    if (review == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    User user = userRepository.findById(userId).orElse(null);

    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id not found.");
    }

    record.setReview(review);
    record.setUser(user);

    return commentRepository.save(record);
  }

  public Comment update(Long commentId, CommentRequest comment, Long userId) {
    Comment record = commentRepository.findById(commentId).orElse(null);

    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id not found.");
    }

    if (record.getUser().getId() != userId) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the comment.");
    }

    record.setContent(comment.getContent());

    return commentRepository.save(record);
  }

  public Comment delete(Long commentId, Long userId) {
    Comment record = commentRepository.findById(commentId).orElse(null);

    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id not found.");
    }

    if (record.getUser().getId() != userId) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the comment.");
    }

    commentRepository.deleteById(commentId);

    return record;
  }
}
