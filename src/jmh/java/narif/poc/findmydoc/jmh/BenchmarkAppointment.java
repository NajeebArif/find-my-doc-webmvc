package narif.poc.findmydoc.jmh;


import narif.poc.findmydoc.model.dto.AppointmentDto;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BenchmarkAppointment {

    public static final String APPOINTMENTS = "http://localhost:8080/appointments";

    @State(Scope.Benchmark)
    public static class AppointmentState{
        public AppointmentDto appointmentDto;
        public RestTemplate restTemplate;
        public HttpEntity<AppointmentDto> entity;
        public List<CompletableFuture<ResponseEntity<String>>> allResults = new ArrayList<>();

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
    @Fork(value = 2, warmups = 5)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    public void bookAppointment(AppointmentState appointmentState, Blackhole blackhole){
        HttpEntity<AppointmentDto> entity = appointmentState.entity;
        RestTemplate restTemplate = appointmentState.restTemplate;
        ResponseEntity<String> appointmentResponse = restTemplate.postForEntity(APPOINTMENTS, entity, String.class);
        int statusCodeValue = appointmentResponse.getStatusCodeValue();
        blackhole.consume(statusCodeValue);
    }

//    @Benchmark
//    @Fork(value = 2, warmups = 5)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @BenchmarkMode(Mode.AverageTime)
//    public void bookAppointmentParallel(AppointmentState appointmentState, Blackhole blackhole){
//        HttpEntity<AppointmentDto> entity = appointmentState.entity;
//        RestTemplate restTemplate = appointmentState.restTemplate;
//        CompletableFuture<ResponseEntity<String>> responseEntityCompletableFuture = CompletableFuture.supplyAsync(() -> restTemplate.postForEntity(APPOINTMENTS, entity, String.class));
//        appointmentState.submitTask(responseEntityCompletableFuture);
//    }

//    @Benchmark
//    @Fork(value = 2, warmups = 5)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @BenchmarkMode(Mode.AverageTime)
//    public void bookAppointmentCollectResponse(AppointmentState appointmentState, Blackhole blackhole){
//        appointmentState.allResults.stream().map(CompletableFuture::join).forEach(blackhole::consume);
//    }

}
