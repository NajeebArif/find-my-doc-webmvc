package narif.poc.findmydoc.model.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class SlotsDto {
    private String slotName;
    private String slotDescription;
    private LocalTime startTime;
    private LocalTime endTime;
}
