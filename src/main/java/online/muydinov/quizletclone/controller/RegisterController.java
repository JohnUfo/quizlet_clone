package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.service.RegisterService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping
    public User register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return registerService.register(user);
    }
}
