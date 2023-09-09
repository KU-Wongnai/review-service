package ku.cs.kuwongnai.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Restaurant {

  @Id
  private Long id;

  // TODO: Add reviews field
}
