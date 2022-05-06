package narif.poc.findmydoc.service;

import narif.poc.findmydoc.model.dto.HospitalDto;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.repo.HospitalRepo;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {
    private final HospitalRepo hospitalRepo;

    public HospitalService(HospitalRepo hospitalRepo) {
        this.hospitalRepo = hospitalRepo;
    }

    public Hospital saveHospitalDto(final HospitalDto hospitalDto){
        return hospitalRepo.save(Hospital.mapHospitalDtoToHospital(hospitalDto));
    }

    public Hospital findHospitalByName(String name){
        return hospitalRepo.findByName(name).orElseThrow(RuntimeException::new);
    }

}
