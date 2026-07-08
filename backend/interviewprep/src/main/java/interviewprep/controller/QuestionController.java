package interviewprep.controller;

import interviewprep.model.Question;
import interviewprep.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{category}/{difficulty}")
    public List<Question> getQuestions(@PathVariable String category,@PathVariable String difficulty) {
        return questionService.getQuestions(
                category,
                difficulty
        );
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return questionService.getAllCategories();
    }

    @GetMapping("/difficulties/{category}")
    public List<String> getDifficultiesByCategory(@PathVariable String category) {
        return questionService.getDifficultiesByCategory(category);
    }
}