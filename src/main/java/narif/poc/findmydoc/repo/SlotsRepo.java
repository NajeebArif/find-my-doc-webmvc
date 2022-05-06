package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.entity.Slots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotsRepo extends JpaRepository<Slots, Long> {
}
