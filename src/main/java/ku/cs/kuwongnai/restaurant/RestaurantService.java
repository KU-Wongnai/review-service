package ku.cs.kuwongnai.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  public Restaurant getRestaurant(Long id) {
    return restaurantRepository.findById(id).orElse(null);
  }

  public Restaurant createRestaurant(RestaurantRequest restaurant) {
    Restaurant record = new Restaurant();
    record.setId(restaurant.getId());
    return restaurantRepository.save(record);
  }

  public Restaurant updateRestaurant(RestaurantRequest restaurant) {
    Restaurant record = restaurantRepository.findById(restaurant.getId()).orElse(null);

    if (record == null) {
      return null;
    }

    record.setId(restaurant.getId());

    return restaurantRepository.save(record);
  }

  public void deleteRestaurant(Long id) {
    restaurantRepository.deleteById(id);
  }
}
