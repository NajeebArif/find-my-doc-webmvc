package narif.poc.findmydoc.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USERS_TABLE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String emailId;
    private String address;
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
