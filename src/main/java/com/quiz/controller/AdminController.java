package com.quiz.controller;

import com.quiz.model.Category;
import com.quiz.model.Difficulty;
import com.quiz.model.Question;
import com.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public String adminHome(Model model, jakarta.servlet.http.HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        List<Question> questions = quizService.getAllQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("categories", Category.values());
        model.addAttribute("difficulties", Difficulty.values());

        // Calcul des compteurs par catégorie côté Java (pas dans Thymeleaf)
        Map<String, Long> countByCategory = new HashMap<>();
        for (Category cat : Category.values()) {
            long count = questions.stream()
                .filter(q -> q.getCategory() == cat)
                .count();
            countByCategory.put(cat.name(), count);
        }
        model.addAttribute("countByCategory", countByCategory);
        model.addAttribute("totalCount", questions.size());

        return "admin/index";
    }

    @GetMapping("/question/new")
    public String newQuestion(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("categories", Category.values());
        model.addAttribute("difficulties", Difficulty.values());
        return "admin/question-form";
    }

    @GetMapping("/question/edit/{id}")
    public String editQuestion(@PathVariable Long id, Model model) {
        Question q = quizService.getQuestion(id);
        if (q == null) return "redirect:/admin";
        model.addAttribute("question", q);
        model.addAttribute("categories", Category.values());
        model.addAttribute("difficulties", Difficulty.values());
        return "admin/question-form";
    }

    @PostMapping("/question/save")
    public String saveQuestion(@ModelAttribute Question question,
                               @RequestParam List<String> options,
                               @RequestParam int correctAnswerIndex,
                               @RequestParam Category category,
                               @RequestParam Difficulty difficulty,
                               @RequestParam(defaultValue = "1") int points) {
        question.setOptions(options);
        question.setCorrectAnswerIndex(correctAnswerIndex);
        question.setCategory(category);
        question.setDifficulty(difficulty);
        question.setPoints(points);
        quizService.saveQuestion(question);
        return "redirect:/admin";
    }

    @PostMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        quizService.deleteQuestion(id);
        return "redirect:/admin";
    }
}
