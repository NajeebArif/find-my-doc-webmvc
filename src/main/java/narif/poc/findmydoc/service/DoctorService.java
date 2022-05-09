package narif.poc.findmydoc.service;

import lombok.extern.slf4j.Slf4j;
import narif.poc.findmydoc.model.dto.DoctorDto;
import narif.poc.findmydoc.model.entity.Doctor;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.model.entity.Slots;
import narif.poc.findmydoc.repo.DoctorRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final HospitalService hospitalService;

    public DoctorService(DoctorRepo doctorRepo, HospitalService hospitalService) {
        this.doctorRepo = doctorRepo;
        this.hospitalService = hospitalService;
    }

    public Doctor saveDoctor(final DoctorDto inputDoc){
        Doctor doctor = new Doctor();
        Hospital hospital = hospitalService.findHospitalByName(inputDoc.getHospitalName());
        doctor.setEmail(inputDoc.getEmail());
        doctor.setName(inputDoc.getDoctorName());
        doctor.setPhone(inputDoc.getPhone());
        doctor.setAvailableSlotsCount(inputDoc.getAvailableSlotsCounts());
        doctor.setHospital(hospital);
        Set<Slots> collect = inputDoc.getAvailableSlots().stream().map(Slots::mapSlotDto).collect(Collectors.toSet());
        collect.forEach(doctor::addSlots);
        return doctorRepo.save(doctor);
    }

    public Doctor findDoctorByName(final String doctorName){
        return doctorRepo.findByName(doctorName)
                .orElseThrow(RuntimeException::new);
    }

    private boolean isThereVacantSlot(Doctor doctor){
        return doctor.getAvailableSlotsCount() >= 1;
    }

    public Doctor bookAppointment(Doctor doctor, int numberOfSlots){
        boolean thereVacantSlot = isThereVacantSlot(doctor);
        String docName = doctor.getName();
        if(!thereVacantSlot){
            log.error("ERROR: No more slots for doctor {}.", docName);
            throw new RuntimeException("No slots available for the doc.");
        }
        Integer availableSlotsCount = doctor.getAvailableSlotsCount();
        log.debug("Current available slots for doctor {} are {}.", docName,availableSlotsCount);
        doctor.setAvailableSlotsCount(availableSlotsCount - numberOfSlots);
        Doctor updatedDoctor = doctorRepo.save(doctor);
        log.debug("1 slot booked for doctor {} and updated in DB.",docName);
        return updatedDoctor;
    }

    @Transactional
    public Doctor areThereVacantSlots(final String doctorName){
        Doctor doctor = doctorRepo.findByName(doctorName)
                .orElseThrow(RuntimeException::new);
        if (doctor.getAvailableSlotsCount()<1){
            throw new RuntimeException("No slots available for the doc.");
        }
        doctor.setAvailableSlotsCount(doctor.getAvailableSlotsCount()-1);
        return doctorRepo.save(doctor);
    }

}
