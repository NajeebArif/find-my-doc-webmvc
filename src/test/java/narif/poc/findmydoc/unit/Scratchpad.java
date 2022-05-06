package narif.poc.findmydoc.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import narif.poc.findmydoc.model.dto.AppointmentDto;
import narif.poc.findmydoc.model.dto.SlotsDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class Scratchpad {

    @Test
    public void test() throws JsonProcessingException {
        SlotsDto slotsDto = new SlotsDto();
        slotsDto.setStartTime(LocalTime.of(9,0));
        slotsDto.setEndTime(LocalTime.NOON);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String s = objectMapper.writeValueAsString(slotsDto);
        assertThat(s).isNotNull();
        System.out.println(s);
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentDate(LocalDate.now());
        String s1 = objectMapper.writeValueAsString(appointmentDto);
        assertThat(s1).isNotNull();
        System.out.println(s1);
    }


}
