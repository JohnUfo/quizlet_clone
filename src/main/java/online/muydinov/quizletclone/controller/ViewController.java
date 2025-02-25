package online.muydinov.quizletclone.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Operation(summary = "Show Cards Page")
    @GetMapping("/cards")
    public String getCardsPage(@RequestParam(name = "cardSetId") Long cardSetId, Model model) {
        model.addAttribute("cardSetId", cardSetId);
        return "cards";
    }

    @Operation(summary = "Show View All Cards Page")
    @GetMapping("/view-cards")
    public String getViewAllCardsPage(@RequestParam(name = "cardSetId") Long cardSetId, Model model) {
        model.addAttribute("cardSetId", cardSetId);
        return "view-cards";
    }
}