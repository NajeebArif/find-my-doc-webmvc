package narif.poc.findmydoc;

import narif.poc.findmydoc.model.dto.DoctorDto;
import narif.poc.findmydoc.model.dto.HospitalDto;
import narif.poc.findmydoc.model.dto.UserDto;
import narif.poc.findmydoc.model.entity.Doctor;
import narif.poc.findmydoc.model.entity.Hospital;
import narif.poc.findmydoc.model.entity.User;
import narif.poc.findmydoc.service.DoctorService;
import narif.poc.findmydoc.service.HospitalService;
import narif.poc.findmydoc.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setup")
public class DataSetupController {

    private final UserService userService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;

    public DataSetupController(UserService userService, HospitalService hospitalService, DoctorService doctorService) {
        this.userService = userService;
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
    }

    @PostMapping("users")
    public User createUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }

    @PostMapping("hospitals")
    public Hospital createHospital(@RequestBody HospitalDto hospitalDto){
        return hospitalService.saveHospitalDto(hospitalDto);
    }

    @PostMapping("doctors")
    public Doctor createDoctor(@RequestBody DoctorDto doctorDto){
        return doctorService.saveDoctor(doctorDto);
    }
}
