package ku.cs.kuwongnai.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.image.Image;
import ku.cs.kuwongnai.image.ImageRepository;
import ku.cs.kuwongnai.restaurant.Restaurant;
import ku.cs.kuwongnai.restaurant.RestaurantRepository;
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
  private RestaurantRepository restaurantRepository;

  public List<Review> findAll() {
    return reviewRepository.findAll();
  }

  public Review findById(Long id) {
    return reviewRepository.findById(id).orElse(null);
  }

  public List<Review> findAllFromRestaurant(Long restaurantId) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

    if (restaurant == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with the given id not found.");
    }

    return restaurant.getReviews();
  }
  public Review create(ReviewRequest review, Long userId, Long restaurantId) {
    User user = userRepository.findById(userId).orElse(null);
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id not found.");
    }

    if (restaurant == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with the given id not found.");
    }

    Review existReview = reviewRepository.findByUserIdAndRestaurantId(userId, restaurantId);

    if (existReview != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "You already reviewed this restaurant.");
    }

    Review record = new Review();
    record.setTitle(review.getTitle());
    record.setContent(review.getContent());
    record.setRating(review.getRating());
    record.setUser(user);
    record.setRestaurant(restaurant);

// Save the review first to generate an ID
    record = reviewRepository.save(record);

// Then save the images with the reference to the saved review
    if (review.getImages() != null) {
      Review finalRecord = record;
      review.getImages().forEach(imageRequest -> {
        Image image = new Image();
        image.setImageUrl(imageRequest.getImageUrl());
        image.setReview(finalRecord);  // now 'record' has an ID
        imageRepository.save(image);
      });
    }

    return record;

  }

  public Review updateById(Long id, ReviewRequest review, Long userId) {
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

  public String deleteById(Long id, Long userId) {
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

    return "Review deleted successfully.";
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
