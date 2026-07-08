package interviewprep.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interviewprep.model.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class InterviewQuestionService {

    private final List<Question> allQuestions = new ArrayList<>();

    @PostConstruct
    public void loadQuestions() {

        try {

            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("questions.json").getInputStream();

            List<Question> questions =mapper.readValue(
                inputStream,
                new TypeReference<List<Question>>() {
            });

            allQuestions.addAll(questions);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load interview questions.", e);
        }

    }

    public List<Question> generateInterviewQuestions() {

        List<Question> interview = new ArrayList<>();
        Set<Long> usedIds = new HashSet<>();

        // Fixed first question
        interview.add(getQuestionByText("Tell me about yourself.", usedIds));

        // Core Technical
        interview.add(getRandomQuestion("Project", "Easy", usedIds));
        interview.add(getRandomQuestion("Java", "Easy", usedIds));
        interview.add(getRandomQuestion("OOP", "Easy", usedIds));
        interview.add(getRandomQuestion("SQL", "Easy", usedIds));

        // Medium
        interview.add(getRandomQuestion("Collections", "Medium", usedIds));
        interview.add(getRandomQuestion("Spring Boot", "Medium", usedIds));
        interview.add(getRandomQuestion("Project", "Medium", usedIds));

        // Hard
        interview.add(getRandomQuestion("DSA", "Medium", usedIds));

        // Fixed Closing HR Question
        interview.add(getRandomClosingQuestion(usedIds));

        return interview;
    }

    private Question getRandomQuestion(String category,String difficulty,Set<Long> usedIds) {

        List<Question> filtered = new ArrayList<>();
        for (Question q : allQuestions) {

            if (q.getCategory().equalsIgnoreCase(category)
                    && q.getDifficulty().equalsIgnoreCase(difficulty)
                    && !usedIds.contains(q.getId())) {
                filtered.add(q);
            }
        }

        if (filtered.isEmpty()) {
            throw new RuntimeException(
                    "No question found for " + category + " (" + difficulty + ")"
            );
        }

        Collections.shuffle(filtered);
        Question selected = filtered.get(0);
        usedIds.add(selected.getId());
        return selected;
    }

    private Question getQuestionByText(String text, Set<Long> usedIds) {

        for (Question q : allQuestions) {
            if (q.getQuestion().equalsIgnoreCase(text)) {
                usedIds.add(q.getId());
                return q;
            }
        }

        throw new RuntimeException("Question not found : " + text);
    }

    private Question getRandomClosingQuestion(Set<Long> usedIds) {

        List<Question> closing = new ArrayList<>();
        for (Question q : allQuestions) {

            String question = q.getQuestion().toLowerCase();
            if (usedIds.contains(q.getId()))
                continue;
            if (question.contains("why should we hire you")
                    || question.contains("where do you see yourself")
                    || question.contains("why do you want")
                    || question.contains("questions for us")) {
                closing.add(q);
            }

        }

        Collections.shuffle(closing);

        return closing.get(0);
    }

}