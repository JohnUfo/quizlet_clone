package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.service.RegisterService;
import online.muydinov.quizletclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User registeredUser = registerService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser.toString());
    }
}
