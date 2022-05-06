package narif.poc.findmydoc.service;

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
        Slots bookedSlot = doctor.getSlots().stream().filter(availableSlots(appointmentDto))
                .findFirst().orElseThrow(() -> new RuntimeException("No Available Slots for the requested time"));
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setHospital(hospital);
        appointment.setDoctor(doctor);
        appointment.setSlots(bookedSlot);
        appointment.setUser(userService.fetchUser(appointmentDto.getUserName()));
        return appointmentRepo.save(appointment);
    }

    private Predicate<Slots> availableSlots(AppointmentDto appointmentDto) {
        return slots -> appointmentDto.getAppointmentTime().isAfter(slots.getStartTime()) && appointmentDto.getAppointmentTime().isBefore(slots.getEndTime());
    }
}
