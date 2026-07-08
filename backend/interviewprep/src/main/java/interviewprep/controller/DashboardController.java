package interviewprep.controller;

import interviewprep.model.Progress;
import interviewprep.repository.ProgressRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final ProgressRepository repository;

    public DashboardController(
            ProgressRepository repository) {

        this.repository = repository;
    }

    @GetMapping
    public Map<String, Object> getStats() {

        Map<String, Object> stats = new HashMap<>();

        long totalQuizzes = repository.count();

        double averageScore =
                repository.findAll()
                        .stream()
                        .mapToDouble(
                                p -> p.getPercentage()
                        )
                        .average()
                        .orElse(0);

        stats.put(
                "totalQuizzes",
                totalQuizzes
        );

        stats.put(
                "averageScore",
                Math.round(averageScore)
        );

        return stats;
    }
}