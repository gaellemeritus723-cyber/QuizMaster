package com.quiz.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class QuizSession {
    private String playerName;
    private Category category;
    private Difficulty difficulty;
    private List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private int earnedPoints = 0;
    private List<Integer> userAnswers = new ArrayList<>();
    private boolean finished = false;

    public Question getCurrentQuestion() {
        if (currentIndex < questions.size()) {
            return questions.get(currentIndex);
        }
        return null;
    }

    public boolean hasNextQuestion() {
        return currentIndex < questions.size() - 1;
    }

    public int getTotalPoints() {
        return questions.stream().mapToInt(Question::getPoints).sum();
    }

    public void answerQuestion(int answerIndex) {
        userAnswers.add(answerIndex);
        Question q = questions.get(currentIndex);
        if (answerIndex == q.getCorrectAnswerIndex()) {
            score++;
            earnedPoints += q.getPoints();
        }
        currentIndex++;
        if (currentIndex >= questions.size()) {
            finished = true;
        }
    }
}
