package ku.cs.kuwongnai.review;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.image.Image;
import ku.cs.kuwongnai.image.ImageRepository;
import ku.cs.kuwongnai.user.User;
import ku.cs.kuwongnai.user.UserRepository;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<Review> findAll() {
    return reviewRepository.findAll();
  }

  public Review findById(Long id) {
    return reviewRepository.findById(id).orElse(null);
  }

  public Review create(ReviewRequest review, Long userId) {
    // Review record = modelMapper.map(review, Review.class);

    User user = userRepository.findById(userId).orElse(null);

    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id not found.");
    }

    Review record = new Review();
    record.setTitle(review.getTitle());
    record.setContent(review.getContent());
    record.setRating(review.getRating());

    // Save review images if it exists.
    if (review.getImages() != null) {
      review.getImages().forEach(imageRequest -> {
        Image image = new Image();
        image.setImageUrl(imageRequest.getImageUrl());
        image.setReview(record);
        imageRepository.save(image);
      });
    }

    record.setUser(user);

    return reviewRepository.save(record);
  }

  public Review updateById(Long id, ReviewRequest review, Long userId) {
    // Review record = modelMapper.map(review, Review.class);
    Review record = reviewRepository.findById(id).orElse(null);

    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    if (record.getUser().getId() != userId) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the review.");
    }

    // Save review images if it exists.
    if (review.getImages() != null) {
      review.getImages().forEach(imageRequest -> {
        Image image = new Image();
        image.setImageUrl(imageRequest.getImageUrl());
        image.setReview(record);
        imageRepository.save(image);
      });
    }

    record.setTitle(review.getTitle());
    record.setContent(review.getContent());
    record.setRating(review.getRating());

    return reviewRepository.save(record);
  }

  public Review deleteById(Long id, Long userId) {
    // Validate if the review exists.
    Review review = reviewRepository.findById(id).orElse(null);

    if (review == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    // Check if the user is the owner of the review.
    if (review.getUser().getId() != userId) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the review.");
    }

    reviewRepository.deleteById(id);

    return review;
  }

  public Review likeById(Long id, Long userId) {
    Review review = reviewRepository.findById(id).orElse(null);

    if (review == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    User user = userRepository.findById(userId).orElse(null);

    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id not found.");
    }

    if (review.getLikes().contains(user)) {
      review.getLikes().remove(user);
    } else {
      review.getLikes().add(user);
    }

    return reviewRepository.save(review);
  }

}
