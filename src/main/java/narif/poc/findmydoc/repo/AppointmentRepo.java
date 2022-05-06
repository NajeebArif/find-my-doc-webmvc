package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
}
