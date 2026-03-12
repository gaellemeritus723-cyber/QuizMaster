package com.quiz.controller;

import com.quiz.model.User;
import com.quiz.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // ── LOGIN ──────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            HttpSession session, Model model) {
        // Déjà connecté → accueil
        if (session.getAttribute("currentUser") != null) return "redirect:/";
        if (error != null) model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect.");
        if (logout != null) model.addAttribute("info", "Vous avez été déconnecté.");
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session) {
        Optional<User> user = authService.login(username, password);
        if (user.isEmpty()) return "redirect:/login?error";

        session.setAttribute("currentUser", user.get());
        // Admin → panel admin, User → accueil
        return user.get().isAdmin() ? "redirect:/admin" : "redirect:/";
    }

    // ── REGISTER ───────────────────────────────────────────
    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (session.getAttribute("currentUser") != null) return "redirect:/";
        return "auth/register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String displayName,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             Model model) {
        String error = authService.register(username, displayName, password, confirmPassword);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("username", username);
            model.addAttribute("displayName", displayName);
            return "auth/register";
        }
        return "redirect:/login?registered";
    }

    // ── LOGOUT ─────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
