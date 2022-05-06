package narif.poc.findmydoc.model.entity;

import lombok.*;
import narif.poc.findmydoc.model.dto.HospitalDto;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String email;
    private String website;
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hospital hospital = (Hospital) o;
        return id != null && Objects.equals(id, hospital.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Hospital mapHospitalDtoToHospital(HospitalDto hospitalDto){
        Hospital hospital = new Hospital();
        hospital.setAddress(hospitalDto.getAddress());
        hospital.setEmail(hospitalDto.getEmail());
        hospital.setName(hospitalDto.getName());
        hospital.setPhone(hospitalDto.getPhone());
        hospital.setWebsite(hospitalDto.getWebsite());
        return hospital;
    }
}
