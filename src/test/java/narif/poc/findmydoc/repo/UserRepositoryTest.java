package narif.poc.findmydoc.repo;

import narif.poc.findmydoc.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave(){
        User user = new User();
        user.setUserName("Najeeb");
        user.setAddress("JP Nagar, Bangalore");
        user.setEmailId("najeeb.arif@example.com");
        user.setPhone("11111111");
        userRepository.save(user);
        List<User> all = userRepository.findAll();
        User user1 = all.get(0);
        assertThat(user1.getUserName()).isEqualTo("Najeeb");
    }

}