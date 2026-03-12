package com.quiz.repository;

import com.quiz.model.Category;
import com.quiz.model.Difficulty;
import com.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(Category category);
    List<Question> findByCategoryAndDifficulty(Category category, Difficulty difficulty);

    @Query("SELECT q FROM Question q WHERE q.category = :category ORDER BY FUNCTION('RAND')")
    List<Question> findByCategoryRandom(Category category);

    @Query("SELECT q FROM Question q WHERE q.category = :category AND q.difficulty = :difficulty ORDER BY FUNCTION('RAND')")
    List<Question> findByCategoryAndDifficultyRandom(Category category, Difficulty difficulty);

    long countByCategory(Category category);
}
