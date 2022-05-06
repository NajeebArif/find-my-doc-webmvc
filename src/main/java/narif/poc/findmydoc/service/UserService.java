package narif.poc.findmydoc.service;

import narif.poc.findmydoc.model.dto.UserDto;
import narif.poc.findmydoc.model.entity.User;
import narif.poc.findmydoc.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(final UserDto userDto){
        return userRepository.save(User.mapUserDtoToUser(userDto));
    }
}
