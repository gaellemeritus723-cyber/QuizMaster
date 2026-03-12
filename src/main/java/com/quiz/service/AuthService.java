package com.quiz.service;

import com.quiz.model.User;
import com.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // BCrypt sans Spring Security — juste le module crypto
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Inscription d'un nouvel utilisateur.
     * Retourne null si succès, ou un message d'erreur.
     */
    public String register(String username, String displayName, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty())
            return "Le nom d'utilisateur est requis.";
        if (username.trim().length() < 3)
            return "Le nom d'utilisateur doit faire au moins 3 caractères.";
        if (password == null || password.length() < 4)
            return "Le mot de passe doit faire au moins 4 caractères.";
        if (!password.equals(confirmPassword))
            return "Les mots de passe ne correspondent pas.";
        if (userRepository.existsByUsername(username.trim().toLowerCase()))
            return "Ce nom d'utilisateur est déjà pris.";

        User user = new User();
        user.setUsername(username.trim().toLowerCase());
        user.setDisplayName(displayName != null && !displayName.trim().isEmpty()
                ? displayName.trim() : username.trim());
        user.setPassword(encoder.encode(password));
        user.setRole(User.Role.USER);
        userRepository.save(user);
        return null; // succès
    }

    /**
     * Connexion. Retourne l'utilisateur si OK, null sinon.
     */
    public Optional<User> login(String username, String password) {
        if (username == null || password == null) return Optional.empty();
        return userRepository.findByUsername(username.trim().toLowerCase())
                .filter(u -> encoder.matches(password, u.getPassword()));
    }
}
