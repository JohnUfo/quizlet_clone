package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public String login(@RequestBody User user) {
        return loginService.verify(user);
    }

}
