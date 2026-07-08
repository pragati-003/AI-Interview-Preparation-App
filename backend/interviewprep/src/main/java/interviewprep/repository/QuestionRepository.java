package interviewprep.repository;

import interviewprep.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;   // <-- ADD THIS IMPORT
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository
        extends JpaRepository<Question, Long> {

    @Query("SELECT DISTINCT q.category FROM Question q ORDER BY q.category")
    List<String> getAllCategories();

    List<Question> findByCategoryAndDifficulty(String category,String difficulty);

    @Query("""
       SELECT DISTINCT q.difficulty
       FROM Question q
       WHERE q.category = :category
       ORDER BY
       CASE q.difficulty
           WHEN 'Easy' THEN 1
           WHEN 'Medium' THEN 2
           WHEN 'Hard' THEN 3
           ELSE 4
       END
       """)
    List<String> getDifficultiesByCategory(@Param("category") String category);

}