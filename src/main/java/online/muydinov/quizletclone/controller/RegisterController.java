package online.muydinov.quizletclone.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.RegisterRequestDTO;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.service.RegisterService;
import online.muydinov.quizletclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration")
public class RegisterController {

    private final RegisterService registerService;
    private final UserService userService;

    @Operation(summary = "User Registration", description = "Registers a new user in the system.")
    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        User register = registerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(register.toString());
    }
}
