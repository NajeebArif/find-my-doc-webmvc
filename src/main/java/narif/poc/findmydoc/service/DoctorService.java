package narif.poc.findmydoc.service;

import narif.poc.findmydoc.model.dto.DoctorDto;
import narif.poc.findmydoc.model.dto.SlotsDto;
import narif.poc.findmydoc.model.entity.Doctor;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.model.entity.Slots;
import narif.poc.findmydoc.repo.DoctorRepo;
import narif.poc.findmydoc.repo.HospitalRepo;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final HospitalRepo hospitalRepo;

    public DoctorService(DoctorRepo doctorRepo, HospitalRepo hospitalRepo) {
        this.doctorRepo = doctorRepo;
        this.hospitalRepo = hospitalRepo;
    }

    public Doctor saveDoctor(final DoctorDto inputDoc){
        Doctor doctor = new Doctor();
        Hospital hospital = hospitalRepo.findByName(inputDoc.getHospitalName())
                .orElseThrow(RuntimeException::new);
        doctor.setEmail(inputDoc.getEmail());
        doctor.setName(inputDoc.getDoctorName());
        doctor.setPhone(inputDoc.getPhone());
        doctor.setAvailableSlotsCount(inputDoc.getAvailableSlotsCounts());
        doctor.setHospital(hospital);
        Set<Slots> collect = inputDoc.getAvailableSlots().stream().map(Slots::mapSlotDto).collect(Collectors.toSet());
        collect.forEach(doctor::addSlots);
        return doctorRepo.save(doctor);
    }

}