package online.muydinov.quizletclone.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @Operation(summary = "Show Register and Login page")
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @Operation(summary = "Show Login Page")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Operation(summary = "Show Register Page")
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @Operation(summary = "Show Dashboard Page")
    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "dashboard";
    }
}
