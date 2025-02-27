package online.muydinov.quizletclone.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.record.RegisterRequestRecord;
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
    public void register(RegisterRequestRecord RegisterRequestRecord) {
        if (RegisterRequestRecord.password() == null || RegisterRequestRecord.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        User user = new User();
        user.setUsername(RegisterRequestRecord.username());
        user.setPassword(passwordEncoder.encode(RegisterRequestRecord.password()));
        user.setFullName(RegisterRequestRecord.fullName());
        userRepository.save(user);
    }
}
