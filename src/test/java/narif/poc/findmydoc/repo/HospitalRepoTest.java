package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.entity.Hospital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HospitalRepoTest {

    public static final String AHMAD_HOSPITALS = "Ahmad Hospitals";
    @Autowired
    private HospitalRepo hospitalRepo;

    @BeforeEach
    public void init(){
        Hospital hospital = createHospital();
        hospitalRepo.save(hospital);
    }

    @Test
    void testHospitalRepo(){
        Hospital hospital1 = hospitalRepo.findAll().get(0);
        assertThat(hospital1.getName()).isEqualTo(AHMAD_HOSPITALS);
    }

    @Test
    void testFindByName(){
        Optional<Hospital> byName = hospitalRepo.findByName(AHMAD_HOSPITALS);
        assertThat(byName).isNotEmpty();
    }

    static Hospital createHospital() {
        Hospital hospital = new Hospital();
        hospital.setName(AHMAD_HOSPITALS);
        hospital.setAddress("Patna, Bihar");
        hospital.setPhone("22222222");
        hospital.setEmail("helpdesk@ahmadhospitals.com");
        hospital.setWebsite("ahmadhospitals.com");
        return hospital;
    }

}