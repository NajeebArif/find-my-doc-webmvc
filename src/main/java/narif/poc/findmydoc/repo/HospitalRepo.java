package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepo extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByName(String name);
}
