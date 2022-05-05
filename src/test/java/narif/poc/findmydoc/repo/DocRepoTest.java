package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.Doctor;
import narif.poc.findmydoc.model.Hospital;
import narif.poc.findmydoc.model.Slots;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DocRepoTest {

    @Autowired
    private DocRepo docRepo;

    @Autowired private HospitalRepo hospitalRepo;

    @Test
    void testDocRepo(){
        Doctor doctor = new Doctor();
        doctor.setName("N. Arif");
        doctor.setEmail("n.arif@example.com");
        doctor.setAvailableSlotsCount(10);
        doctor.setPhone("12121212");
        Hospital hospital = saveHospital();
        doctor.setHospital(hospital);
        doctor.setSlots(Slots.THIRD_SLOT);
        docRepo.save(doctor);
        Doctor doctor1 = docRepo.findAll().get(0);
        assertThat(doctor1.getName()).isEqualTo("N. Arif");
    }

    private Hospital saveHospital() {
        Hospital hospital = HospitalRepoTest.createHospital();
        return hospitalRepo.save(hospital);
    }

}