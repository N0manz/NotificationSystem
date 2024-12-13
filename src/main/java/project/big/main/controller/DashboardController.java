package project.big.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/{userId}")
    public String getDashboardPage(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId); // Передаем user_id в шаблон
        return "dashboard"; // Возвращает dashboard.html
    }
}
