package online.muydinov.quizletclone.service;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

}
