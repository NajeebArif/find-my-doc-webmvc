package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.Hospital;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HospitalRepoTest {

    @Autowired
    private HospitalRepo hospitalRepo;

    @Test
    void testHospitalRepo(){
        Hospital hospital = createHospital();
        hospitalRepo.save(hospital);
        Hospital hospital1 = hospitalRepo.findAll().get(0);
        assertThat(hospital1.getName()).isEqualTo("Ahmad Hospitals");
    }

    static Hospital createHospital() {
        Hospital hospital = new Hospital();
        hospital.setName("Ahmad Hospitals");
        hospital.setAddress("Patna, Bihar");
        hospital.setPhone("22222222");
        hospital.setEmail("helpdesk@ahmadhospitals.com");
        hospital.setWebsite("ahmadhospitals.com");
        return hospital;
    }

}