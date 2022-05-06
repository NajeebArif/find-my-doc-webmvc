package narif.poc.findmydoc.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDto {
    private String doctorName;
    private String hospitalName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String userName;
}
