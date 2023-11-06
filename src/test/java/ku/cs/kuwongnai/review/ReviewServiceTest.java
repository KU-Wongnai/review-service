package ku.cs.kuwongnai.review;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ku.cs.kuwongnai.restaurant.Restaurant;
import ku.cs.kuwongnai.restaurant.RestaurantRepository;
import ku.cs.kuwongnai.restaurant.RestaurantService;
import ku.cs.kuwongnai.user.User;
import ku.cs.kuwongnai.user.UserRepository;
import ku.cs.kuwongnai.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        // Create a list of mock reviews
        List<Review> mockReviews = new ArrayList<>();
        when(reviewRepository.findAll()).thenReturn(mockReviews);

        List<Review> result = reviewService.findAll();

        assertEquals(mockReviews, result);
    }

    @Test
    public void testFindById() {
        Long reviewId = 1L;
        Review mockReview = new Review();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));

        Review result = reviewService.findById(reviewId);

        assertEquals(mockReview, result);
    }

    @Test
    public void testFindAllFromRestaurant() {
        Long restaurantId = 1L;
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setId(restaurantId);
        List<Review> mockReviews = new ArrayList<>();
        mockRestaurant.setReviews(mockReviews);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(mockRestaurant));

        List<Review> result = reviewService.findAllFromRestaurant(restaurantId);

        assertEquals(mockReviews, result);
    }

    @Test
    public void testCreate() {
        // Set up mock data and expectations
        ReviewRequest reviewRequest = new ReviewRequest();
        Long userId = 1L;
        Long restaurantId = 2L;
        User mockUser = new User();
        Restaurant mockRestaurant = new Restaurant();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(mockRestaurant));
        when(reviewRepository.findByUserIdAndRestaurantId(userId, restaurantId)).thenReturn(null);

        Review mockReview = new Review();
        when(reviewRepository.save(any(Review.class))).thenReturn(mockReview);

        // Call the service method
        Review result = reviewService.create(reviewRequest, userId, restaurantId);

        // Assertions
        assertEquals(mockReview, result);
    }

    @Test
    public void testUpdateById() {
        Long reviewId = 1L;
        Long userId = 2L;
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setTitle("Updated Title");
        reviewRequest.setContent("Updated Content");
        reviewRequest.setRating(4.5);

        Review mockReview = new Review();
        mockReview.setId(reviewId);
        User mockUser = new User();
        mockUser.setId(userId);
        mockReview.setUser(mockUser);

        // Mock repository behavior
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(reviewRepository.save(any(Review.class))).thenReturn(mockReview);

        Review updatedReview = reviewService.updateById(reviewId, reviewRequest, userId);

        assertEquals("Updated Title", updatedReview.getTitle());
        assertEquals("Updated Content", updatedReview.getContent());
        assertEquals(4.5, updatedReview.getRating(), 0.01);
    }

    @Test
    public void testDeleteById() {
        Long reviewId = 1L;
        Long userId = 2L;

        Review mockReview = new Review();
        mockReview.setId(reviewId);
        User mockUser = new User();
        mockUser.setId(userId);
        mockReview.setUser(mockUser);

        // Mock repository behavior
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        String result = reviewService.deleteById(reviewId, userId);

        assertEquals("Review deleted successfully.", result);
    }

    @Test
    public void testLikeById() {
        Long reviewId = 1L;
        Long userId = 2L;

        Review mockReview = new Review();
        mockReview.setId(reviewId);
        User mockUser = new User();
        mockUser.setId(userId);

        // Initialize the likes property with an empty list
        mockReview.setLikes(new ArrayList<>());

        // Mock repository behavior
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Mock the save behavior to return the updated review
        when(reviewRepository.save(any(Review.class))).thenReturn(mockReview);

        // Initially, the user has not liked the review
        assertFalse(mockReview.getLikes().contains(mockUser));

        // Like the review
        Review likedReview = reviewService.likeById(reviewId, userId);

        // Now, the user should have liked the review
        assertTrue(likedReview.getLikes().contains(mockUser));

        // Dislike the review
        Review dislikedReview = reviewService.likeById(reviewId, userId);

        // Now, the user should have disliked the review
        assertFalse(dislikedReview.getLikes().contains(mockUser));
    }



}
