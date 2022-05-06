package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepo extends JpaRepository<Hospital, Long> {
}
