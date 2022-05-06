package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AppointmentRepoTest {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private DocRepo docRepo;

    @Autowired
    private UserRepository userRepository;

    private Hospital hospital;
    private Doctor doctor;
    private Slots slots;
    private User user;

    @BeforeEach
    public void init(){
        saveDoctor();
        saveUser();
    }

    @Test
    void testSaveAppointment(){
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setDoctor(doctor);
        appointment.setHospital(hospital);
        appointment.setSlots(slots);
        appointment.setUser(user);
        appointmentRepo.save(appointment);
        Appointment appointment1 = appointmentRepo.findAll().get(0);
        assertThat(appointment1).isNotNull();
        assertThat(appointment1.getUser()).isNotNull();
        assertThat(appointment1.getUser().getUserName()).isNotNull().isEqualTo("Najeeb");

    }

    private void saveUser() {
        user = new User();
        user.setUserName("Najeeb");
        user.setAddress("JP Nagar, Bangalore");
        user.setEmailId("najeeb.arif@example.com");
        user.setPhone("11111111");
        userRepository.save(user);
    }

    private void saveDoctor() {
        doctor = new Doctor();
        doctor.setName("N. Arif");
        doctor.setEmail("n.arif@example.com");
        doctor.setAvailableSlotsCount(10);
        doctor.setPhone("12121212");
        Hospital hospital = saveHospital();
        doctor.setHospital(hospital);
        Slots slots = getSlots();
        doctor.addSlots(slots);
        docRepo.save(doctor);
    }

    private Slots getSlots() {
        slots = new Slots();
        slots.setSlotName("Morning Slot.");
        slots.setSlotDescription("This is the first slot, 9 am - 12 noon.");
        slots.setStartTime(LocalTime.of(9,0));
        slots.setEndTime(LocalTime.NOON);
        return slots;
    }

    private Hospital saveHospital() {
        hospital = HospitalRepoTest.createHospital();
        return hospital;
    }



}