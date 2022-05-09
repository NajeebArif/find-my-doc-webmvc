package narif.poc.findmydoc.unit;

import narif.poc.findmydoc.model.dto.AppointmentDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentLoadTest {

    public static final String APPOINTMENTS = "http://localhost:8080/appointments";

    public RestTemplate restTemplate = new RestTemplate();

    Executor executor = Executors.newFixedThreadPool(30);

    @Test
    void loadTestAppointmentApi(){
        List<CompletableFuture<ResponseEntity<String>>> collect = createListOfAppointments().stream()
                .map(HttpEntity::new)
                .map(this::submitRestRequestInCf)
                .collect(Collectors.toList());

        Map<Integer, Long> collect1 = collect.stream().map(this::getJoin)
                .mapToInt(this::getStatusCodeValue)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        System.out.println(collect1);
    }

    private ResponseEntity<String> getJoin(CompletableFuture<ResponseEntity<String>> cf) {
        try{
            return cf.join();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    private int getStatusCodeValue(ResponseEntity<String> re) {
        try {
            return re.getStatusCodeValue();
        }catch (Exception e){
            return 500;
        }

    }

    private CompletableFuture<ResponseEntity<String>> submitRestRequestInCf(HttpEntity<AppointmentDto> appointmentDtoHttpEntity) {
        return CompletableFuture.supplyAsync(() -> restTemplate.postForEntity(APPOINTMENTS, appointmentDtoHttpEntity, String.class), executor);
    }

    private List<AppointmentDto> createListOfAppointments(){
        List<AppointmentDto> appointmentDtos = Arrays.asList(
                createAppointment("Najeeb","Gourav","AIIMS, New Delhi"),
                createAppointment("JohnDoe","Khadija","AIIMS, Patna"),
                createAppointment("JohnDoe","Neha","AIIMS, New Delhi"),
                createAppointment("JaneDoe","Sahil","AIIMS, Bangalore"),
                createAppointment("ArunTG","Deepa","AIIMS, Bombay"),
                createAppointment("Rishi","Arif","AIIMS, Patna"),
                createAppointment("Mannu","Bharat","AIIMS, Chennai"),

                createAppointment("Mayank","Bharat","AIIMS, Chennai"),
                createAppointment("Chandan","Neha","AIIMS, New Delhi"),
                createAppointment("Prasanjeet","Deepa","AIIMS, Bombay"),
                createAppointment("Najeeb","Khadija","AIIMS, Patna"),
                createAppointment("JohnDoe","Arif","AIIMS, Patna"),
                createAppointment("JaneDoe","Gourav","AIIMS, New Delhi"),
                createAppointment("JohnDoe","Sahil","AIIMS, Bangalore"),

                createAppointment("JaneDoe","Khadija","AIIMS, Patna"),
                createAppointment("ArunTG","Bharat","AIIMS, Chennai"),
                createAppointment("Rishi","Neha","AIIMS, New Delhi"),
                createAppointment("Mannu","Arif","AIIMS, Patna"),
                createAppointment("Mayank","Deepa","AIIMS, Bombay"),
                createAppointment("Chandan","Sahil","AIIMS, Bangalore"),
                createAppointment("Prasanjeet","Gourav","AIIMS, New Delhi")

        );
        return appointmentDtos;

    }

    private AppointmentDto createAppointment(String userName, String doctorName, String hospitalName) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentDate(LocalDate.now().plusDays(1));
        appointmentDto.setAppointmentTime(LocalTime.of(14,0));
        appointmentDto.setUserName(userName);
        appointmentDto.setDoctorName(doctorName);
        appointmentDto.setHospitalName(hospitalName);
        return appointmentDto;
    }
}
