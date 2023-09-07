package ku.cs.kuwongnai.comment;

import org.modelmapper.ModelMapper;
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

  @Autowired
  private ModelMapper modelMapper;

  public Comment create(CommentRequest comment) {
    // Comment record = modelMapper.map(comment, Comment.class);
    Comment record = new Comment();
    record.setContent(comment.getContent());

    Review review = reviewRepository.findById(comment.getReviewId()).orElse(null);

    if (review == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    User user = userRepository.findById(comment.getUserId()).orElse(null);

    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id not found.");
    }

    record.setReview(review);
    record.setUser(user);

    return commentRepository.save(record);
  }

  public Comment update(Long commentId, CommentUpdateRequest comment) {
    Comment record = commentRepository.findById(commentId).orElse(null);

    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id not found.");
    }

    record.setContent(comment.getContent());

    return commentRepository.save(record);
  }

  public Comment delete(Long commentId) {
    Comment record = commentRepository.findById(commentId).orElse(null);

    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id not found.");
    }

    commentRepository.deleteById(commentId);

    return record;
  }
}
