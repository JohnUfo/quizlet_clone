package online.muydinov.quizletclone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class homeController {

    @GetMapping
    public String greet(HttpServletRequest request) {
        return "Welcome to Home page "+request.getSession().getId();
    }

}