package ku.cs.kuwongnai.user;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

  @Id
  private Long id;
  private String name;
  private String email;
  private LocalDateTime emailVerifiedAt;
  private String avatar;
}
