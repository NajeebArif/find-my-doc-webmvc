package narif.poc.findmydoc.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorDto {
    private String doctorName;
    private String email;
    private String phone;
    private Integer availableSlotsCounts;
    private String hospitalName;
    private List<SlotsDto> availableSlots;
}
