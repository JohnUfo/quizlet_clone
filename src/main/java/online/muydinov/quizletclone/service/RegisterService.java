package online.muydinov.quizletclone.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.RegisterRequestDTO;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequestDTO registerRequestDTO) {
        if (registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setFullName(registerRequestDTO.getFullName());
        userRepository.save(user);
    }
}
