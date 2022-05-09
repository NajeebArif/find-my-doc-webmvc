package narif.poc.findmydoc;

import lombok.extern.slf4j.Slf4j;
import narif.poc.findmydoc.model.dto.AppointmentDto;
import narif.poc.findmydoc.model.entity.Appointment;
import narif.poc.findmydoc.service.AppointmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointments")
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentDto appointmentDto){
        log.info("Creating appointment for user {} with doctor {}.",
                appointmentDto.getUserName(),appointmentDto.getDoctorName());
        return appointmentService.bookAnotherAppointment(appointmentDto);
//        return appointmentService.bookAnAppointment(appointmentDto);
    }
}
