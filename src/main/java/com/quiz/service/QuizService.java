package com.quiz.service;

import com.quiz.model.*;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    public QuizSession createSession(String playerName, Category category, Difficulty difficulty, int numQuestions) {
        List<Question> questions;
        if (difficulty != null) {
            questions = questionRepository.findByCategoryAndDifficultyRandom(category, difficulty);
        } else {
            questions = questionRepository.findByCategoryRandom(category);
        }

        // Limit number of questions
        if (questions.size() > numQuestions) {
            questions = questions.subList(0, numQuestions);
        }

        QuizSession session = new QuizSession();
        session.setPlayerName(playerName);
        session.setCategory(category);
        session.setDifficulty(difficulty);
        session.setQuestions(questions);
        return session;
    }

    public QuizResult saveResult(QuizSession session) {
        QuizResult result = new QuizResult();
        result.setPlayerName(session.getPlayerName());
        result.setCategory(session.getCategory());
        result.setDifficulty(session.getDifficulty());
        result.setScore(session.getScore());
        result.setTotalQuestions(session.getQuestions().size());
        result.setTotalPoints(session.getTotalPoints());
        result.setEarnedPoints(session.getEarnedPoints());
        return quizResultRepository.save(result);
    }

    public List<QuizResult> getTopScores() {
        return quizResultRepository.findTop10ByOrderByEarnedPointsDesc();
    }

    public List<QuizResult> getTopScoresByCategory(Category category) {
        return quizResultRepository.findTop10ByCategoryOrderByEarnedPointsDesc(category);
    }

    public long countByCategory(Category category) {
        return questionRepository.countByCategory(category);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
}
