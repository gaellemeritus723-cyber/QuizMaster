package com.quiz.controller;

import com.quiz.model.Category;
import com.quiz.model.Difficulty;
import com.quiz.model.QuizSession;
import com.quiz.model.User;
import com.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private QuizService quizService;

    // Helper : récupère l'utilisateur connecté depuis la session HTTP
    private User currentUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        model.addAttribute("currentUser", currentUser(session));
        model.addAttribute("categories", Category.values());
        model.addAttribute("anglaisCount", quizService.countByCategory(Category.ANGLAIS));
        model.addAttribute("mathsCount", quizService.countByCategory(Category.MATHS));
        model.addAttribute("chimieCount", quizService.countByCategory(Category.CHIMIE));
        model.addAttribute("topScores", quizService.getTopScores());
        return "index";
    }

    @GetMapping("/quiz/start")
    public String startForm(@RequestParam Category category, HttpSession session, Model model) {
        model.addAttribute("currentUser", currentUser(session));
        model.addAttribute("category", category);
        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("questionCount", quizService.countByCategory(category));
        return "quiz/start";
    }

    @PostMapping("/quiz/begin")
    public String beginQuiz(@RequestParam String playerName,
                            @RequestParam Category category,
                            @RequestParam(required = false) Difficulty difficulty,
                            @RequestParam(defaultValue = "10") int numQuestions,
                            HttpSession session) {
        QuizSession quizSession = quizService.createSession(playerName, category, difficulty, numQuestions);
        if (quizSession.getQuestions().isEmpty()) return "redirect:/?error=noQuestions";
        session.setAttribute("quizSession", quizSession);
        return "redirect:/quiz/question";
    }

    @GetMapping("/quiz/question")
    public String showQuestion(HttpSession session, Model model) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");
        if (quizSession == null || quizSession.isFinished()) return "redirect:/";
        model.addAttribute("currentUser", currentUser(session));
        model.addAttribute("quizData", quizSession);
        model.addAttribute("question", quizSession.getCurrentQuestion());
        model.addAttribute("questionNumber", quizSession.getCurrentIndex() + 1);
        model.addAttribute("totalQuestions", quizSession.getQuestions().size());
        model.addAttribute("progress", (quizSession.getCurrentIndex() * 100) / quizSession.getQuestions().size());
        return "quiz/question";
    }

    @PostMapping("/quiz/answer")
    public String answerQuestion(@RequestParam int answerIndex, HttpSession session) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");
        if (quizSession == null) return "redirect:/";
        quizSession.answerQuestion(answerIndex);
        session.setAttribute("quizSession", quizSession);
        return quizSession.isFinished() ? "redirect:/quiz/result" : "redirect:/quiz/question";
    }

    @GetMapping("/quiz/result")
    public String showResult(HttpSession session, Model model) {
        QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");
        if (quizSession == null) return "redirect:/";
        var result = quizService.saveResult(quizSession);
        model.addAttribute("currentUser", currentUser(session));
        model.addAttribute("result", result);
        model.addAttribute("quizData", quizSession);
        session.removeAttribute("quizSession");
        return "quiz/result";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(HttpSession session, Model model) {
        model.addAttribute("currentUser", currentUser(session));
        model.addAttribute("topScores", quizService.getTopScores());
        model.addAttribute("categories", Category.values());
        for (Category cat : Category.values()) {
            model.addAttribute("top" + cat.name(), quizService.getTopScoresByCategory(cat));
        }
        return "leaderboard";
    }
}
