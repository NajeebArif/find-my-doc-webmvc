package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
}
