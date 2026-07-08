package interviewprep.service;

import interviewprep.model.Question;
import org.springframework.stereotype.Service;

import interviewprep.repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<Question> getQuestions(String category,String difficulty) {
        return repository.findByCategoryAndDifficulty(
                category,
                difficulty
        );
    }

    public List<String> getAllCategories() {
        return repository.getAllCategories();
    }

    public List<String> getDifficultiesByCategory(String category) {
        return repository.getDifficultiesByCategory(category);
    }

}