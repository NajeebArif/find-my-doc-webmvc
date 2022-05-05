package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
