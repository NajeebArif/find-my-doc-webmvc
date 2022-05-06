package narif.poc.findmydoc.jmh;


import narif.poc.findmydoc.model.dto.AppointmentDto;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class BenchmarkAppointment {

    public static final String APPOINTMENTS = "http://localhost:8080/appointments";

    @State(Scope.Benchmark)
    public static class AppointmentState{
        public AppointmentDto appointmentDto;
        public RestTemplate restTemplate;
        public HttpEntity<AppointmentDto> entity;

        @Setup(Level.Invocation)
        public void init(){
            appointmentDto = createAppointment("Najeeb", "Arif","AIIMS, Patna");
            entity = new HttpEntity<>(appointmentDto);
            restTemplate = new RestTemplate();
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

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void init(AppointmentState appointmentState, Blackhole blackhole){
        HttpEntity<AppointmentDto> entity = appointmentState.entity;
        RestTemplate restTemplate = appointmentState.restTemplate;
        ResponseEntity<String> appointmentResponse = restTemplate.postForEntity(APPOINTMENTS, entity, String.class);
        int statusCodeValue = appointmentResponse.getStatusCodeValue();
        blackhole.consume(statusCodeValue);
    }

}
