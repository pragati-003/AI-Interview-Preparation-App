package interviewprep.model;

import java.util.ArrayList;
import java.util.List;

public class InterviewSession {

    private String sessionId;

    // Current question number (1 to 10)
    private int currentQuestion = 1;

    private boolean completed = false;

    // Conversation history sent to Gemini
    private List<String> conversationHistory = new ArrayList<>();

    // 10 selected interview questions
    private List<Question> interviewQuestions = new ArrayList<>();

    private List<InterviewResult> results = new ArrayList<>();

    // Current question index (0 to 9)
    private int currentQuestionIndex = 0;

    public InterviewSession() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<String> getConversationHistory() {
        return conversationHistory;
    }

    public void setConversationHistory(List<String> conversationHistory) {
        this.conversationHistory = conversationHistory;
    }

    public void addConversation(String message) {
        this.conversationHistory.add(message);
    }

    public List<Question> getInterviewQuestions() {
        return interviewQuestions;
    }

    public void setInterviewQuestions(List<Question> interviewQuestions) {
        this.interviewQuestions = interviewQuestions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    // Returns current interview question
    public Question getCurrentInterviewQuestion() {

        if (currentQuestionIndex >= interviewQuestions.size()) {
            return null;
        }

        return interviewQuestions.get(currentQuestionIndex);
    }

    // Move to next question
    public void nextQuestion() {

        currentQuestionIndex++;
        currentQuestion++;

        if (currentQuestionIndex >= interviewQuestions.size()) {
            completed = true;
        }
    }

    public List<InterviewResult> getResults() {
        return results;
    }

    public void setResults(List<InterviewResult> results) {
        this.results = results;
    }

    public void addResult(InterviewResult result) {
        this.results.add(result);
    }

}