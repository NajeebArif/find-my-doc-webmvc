package narif.poc.findmydoc.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Integer availableSlotsCount;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST)
    private Set<Slots> slots = new HashSet<>();
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;
    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Doctor doctor = (Doctor) o;
        return id != null && Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addSlots(Slots slots) {
        this.getSlots().add(slots);
        slots.setDoctor(this);
    }
}
