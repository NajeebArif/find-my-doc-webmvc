package narif.poc.findmydoc.service;

import narif.poc.findmydoc.model.dto.DoctorDto;
import narif.poc.findmydoc.model.entity.Doctor;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.model.entity.Slots;
import narif.poc.findmydoc.repo.DoctorRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
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
