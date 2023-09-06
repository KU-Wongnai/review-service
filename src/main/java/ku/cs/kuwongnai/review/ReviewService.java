package ku.cs.kuwongnai.review;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.user.User;
import ku.cs.kuwongnai.user.UserRepository;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<Review> findAll() {
    return reviewRepository.findAll();
  }

  public Review findById(Long id) {
    return reviewRepository.findById(id).orElse(null);
  }

  public Review create(ReviewRequest review) {
    Review record = modelMapper.map(review, Review.class);

    User user = userRepository.findById(review.getUserId()).orElse(null);

    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id not found.");
    }

    record.setUser(user);

    return reviewRepository.save(record);
  }

  public Review updateById(Long id, ReviewRequest review) {
    // Review record = modelMapper.map(review, Review.class);
    Review record = reviewRepository.findById(id).orElse(null);

    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    if (record.getUser().getId() != review.getUserId()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the review.");
    }

    record.setTitle(review.getTitle());
    record.setContent(review.getContent());
    record.setRating(review.getRating());

    return reviewRepository.save(record);
  }

  public Review deleteById(Long id) {
    // TODO: Validate if the review exists.
    Review review = reviewRepository.findById(id).orElse(null);

    if (review == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    // TODO: Check if the user is the owner of the review.

    reviewRepository.deleteById(id);

    return review;
  }

}
