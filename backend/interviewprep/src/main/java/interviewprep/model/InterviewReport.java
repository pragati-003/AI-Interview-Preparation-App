package interviewprep.model;

import java.util.List;

public class InterviewReport {

    private int overallScore;
    private int technicalScore;
    private int grammarScore;
    private int communicationScore;
    private int vocabularyScore;
    private int confidenceScore;
    private double speechRate;
    private int fluencyScore;
    private int averageFillerWords;
    private String speechFeedback;

    private String aiSummary;
    private List<String> strongAreas;
    private List<String> improvementAreas;
    private int questionsAttempted;

    private List<interviewprep.model.QuestionPerformance> questionResults;

    public InterviewReport() {
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public int getTechnicalScore() {
        return technicalScore;
    }

    public void setTechnicalScore(int technicalScore) {
        this.technicalScore = technicalScore;
    }

    public int getGrammarScore() {
        return grammarScore;
    }

    public void setGrammarScore(int grammarScore) {
        this.grammarScore = grammarScore;
    }

    public int getCommunicationScore() {
        return communicationScore;
    }

    public void setCommunicationScore(int communicationScore) {
        this.communicationScore = communicationScore;
    }

    public int getVocabularyScore() {
        return vocabularyScore;
    }

    public void setVocabularyScore(int vocabularyScore) {
        this.vocabularyScore = vocabularyScore;
    }

    public int getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(int confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public List<String> getStrongAreas() {
        return strongAreas;
    }

    public void setStrongAreas(List<String> strongAreas) {
        this.strongAreas = strongAreas;
    }

    public List<String> getImprovementAreas() {
        return improvementAreas;
    }

    public void setImprovementAreas(List<String> improvementAreas) {
        this.improvementAreas = improvementAreas;
    }

    public int getQuestionsAttempted() {
        return questionsAttempted;
    }

    public void setQuestionsAttempted(int questionsAttempted) {
        this.questionsAttempted = questionsAttempted;
    }

    public List<interviewprep.model.QuestionPerformance> getQuestionResults() {
        return questionResults;
    }

    public void setQuestionResults(List<interviewprep.model.QuestionPerformance> questionResults) {
        this.questionResults = questionResults;
    }

    public int getFluencyScore() {
        return fluencyScore;
    }

    public void setFluencyScore(int fluencyScore) {
        this.fluencyScore = fluencyScore;
    }

    public int getAverageFillerWords() {
        return averageFillerWords;
    }

    public void setAverageFillerWords(int averageFillerWords) {
        this.averageFillerWords = averageFillerWords;
    }

    public double getSpeechRate() {
        return speechRate;
    }

    public void setSpeechRate(double speechRate) {
        this.speechRate = speechRate;
    }

    public String getSpeechFeedback() {
        return speechFeedback;
    }

    public void setSpeechFeedback(String speechFeedback) {
        this.speechFeedback = speechFeedback;
    }
}
