package narif.poc.findmydoc.service;

import narif.poc.findmydoc.model.dto.HospitalDto;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.repo.HospitalRepo;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class HospitalService {
    private final HospitalRepo hospitalRepo;

    public HospitalService(HospitalRepo hospitalRepo) {
        this.hospitalRepo = hospitalRepo;
    }

    public Hospital saveHospitalDto(final HospitalDto hospitalDto){
        hospitalRepo.findByName(hospitalDto.getName()).ifPresent(rejectSave());
        return hospitalRepo.save(Hospital.mapHospitalDtoToHospital(hospitalDto));
    }

    private Consumer<Hospital> rejectSave() {
        return hospital -> {
            throw new RuntimeException("Hospital Already registered with the given name: " + hospital.getName());
        };
    }

    public Hospital findHospitalByName(String name){
        return hospitalRepo.findByName(name).orElseThrow(RuntimeException::new);
    }

}
