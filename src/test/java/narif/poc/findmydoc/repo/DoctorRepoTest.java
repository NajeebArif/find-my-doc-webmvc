package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.entity.Doctor;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.model.entity.Slots;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DoctorRepoTest {

    @Autowired
    private DoctorRepo doctorRepo;

    @Test
    void testDocRepo(){
        saveDoctor();
        Doctor doctor1 = doctorRepo.findAll().get(0);
        assertThat(doctor1.getName()).isEqualTo("N. Arif");
        System.out.println(doctor1);
    }

    private void saveDoctor() {
        Doctor doctor = new Doctor();
        doctor.setName("N. Arif");
        doctor.setEmail("n.arif@example.com");
        doctor.setAvailableSlotsCount(10);
        doctor.setPhone("12121212");
        Hospital hospital = saveHospital();
        doctor.setHospital(hospital);
        Slots slots = getSlots();
        doctor.addSlots(slots);
        doctorRepo.save(doctor);
    }

    private Slots getSlots() {
        Slots slots = new Slots();
        slots.setSlotName("Morning Slot.");
        slots.setSlotDescription("This is the first slot, 9 am - 12 noon.");
        slots.setStartTime(LocalTime.of(9,0));
        slots.setEndTime(LocalTime.NOON);
        return slots;
    }

    private Hospital saveHospital() {
        return HospitalRepoTest.createHospital();
    }

}