package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.RegisterRequestDTO;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.service.RegisterService;
import online.muydinov.quizletclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        User register = registerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(register.toString());
    }
}
