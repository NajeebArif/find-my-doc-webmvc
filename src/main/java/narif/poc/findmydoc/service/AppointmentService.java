package narif.poc.findmydoc.service;

import lombok.extern.slf4j.Slf4j;
import narif.poc.findmydoc.model.dto.AppointmentDto;
import narif.poc.findmydoc.model.entity.Appointment;
import narif.poc.findmydoc.model.entity.Doctor;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.model.entity.Slots;
import narif.poc.findmydoc.repo.AppointmentRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Predicate;

@Service
@Slf4j
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final DoctorService doctorService;
    private final UserService userService;

    public AppointmentService(AppointmentRepo appointmentRepo, DoctorService doctorService, UserService userService) {
        this.appointmentRepo = appointmentRepo;
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @Transactional
    public Appointment bookAnAppointment(AppointmentDto appointmentDto){
        Doctor doctor = doctorService.areThereVacantSlots(appointmentDto.getDoctorName());
        Hospital hospital = doctor.getHospital();
        Slots bookedSlot = getBookedSlot(appointmentDto, doctor);
        Appointment appointment = createAppointment(appointmentDto, doctor, hospital, bookedSlot);
        return appointmentRepo.save(appointment);
    }

    @Transactional
    public Appointment bookAnotherAppointment(AppointmentDto appointmentDto){
        Doctor doctorByName = doctorService.findDoctorByName(appointmentDto.getDoctorName());
        Doctor doctorsInfo = doctorService.bookAppointment(doctorByName, 1);
        log.info("Doctors slot booked.");
        Hospital hospital = doctorsInfo.getHospital();
        Slots bookedSlot = getBookedSlot(appointmentDto, doctorsInfo);
        Appointment appointment = createAppointment(appointmentDto, doctorsInfo, hospital, bookedSlot);
        log.info("Booking appointment.");
        return appointmentRepo.save(appointment);
    }

    private Slots getBookedSlot(AppointmentDto appointmentDto, Doctor doctor) {
        return doctor.getSlots().stream()
                .filter(availableSlots(appointmentDto))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Available Slots for the requested time"));
    }

    private Appointment createAppointment(AppointmentDto appointmentDto, Doctor doctor, Hospital hospital, Slots bookedSlot) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setHospital(hospital);
        appointment.setDoctor(doctor);
        appointment.setSlots(bookedSlot);
        appointment.setUser(userService.fetchUser(appointmentDto.getUserName()));
        return appointment;
    }

    private Predicate<Slots> availableSlots(AppointmentDto appointmentDto) {
        return slots -> appointmentDto.getAppointmentTime().isAfter(slots.getStartTime()) && appointmentDto.getAppointmentTime().isBefore(slots.getEndTime());
    }
}
