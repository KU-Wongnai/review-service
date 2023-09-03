package ku.cs.kuwongnai.review;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository repository;

  @Autowired
  private ModelMapper modelMapper;

  public List<Review> findAll() {
    return repository.findAll();
  }

  public Review findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Review create(ReviewRequest review) {
    Review record = modelMapper.map(review, Review.class);

    return repository.save(record);
  }

  public Review updateById(Long id, ReviewRequest review) {
    // Review record = modelMapper.map(review, Review.class);
    Review record = repository.findById(id).orElse(null);

    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    record.setTitle(review.getTitle());
    record.setContent(review.getContent());
    record.setRating(review.getRating());

    return repository.save(record);
  }

  public Review deleteById(Long id) {
    // TODO: Validate if the review exists.
    Review review = repository.findById(id).orElse(null);

    if (review == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id not found.");
    }

    // TODO: Check if the user is the owner of the review.

    repository.deleteById(id);

    return review;
  }

}
