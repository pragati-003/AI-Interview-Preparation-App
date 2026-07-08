package interviewprep.model;

import java.util.List;

public class InterviewResponse {

    private String sessionId;

    private int questionNumber;

    private String question;

    private boolean interviewCompleted;

    private int overallScore;
    private int technicalScore;
    private int grammarScore;
    private int communicationScore;
    private int vocabularyScore;
    private int confidenceScore;
    private InterviewReport finalReport;

    private double speechRate;
    private int fillerWords;
    private int fluencyScore;

    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> suggestions;

    private String idealAnswer;

    public InterviewResponse() {
    }

    public InterviewReport getFinalReport() {
        return finalReport;
    }

    public void setFinalReport(InterviewReport finalReport) {
        this.finalReport = finalReport;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isInterviewCompleted() {
        return interviewCompleted;
    }

    public void setInterviewCompleted(boolean interviewCompleted) {
        this.interviewCompleted = interviewCompleted;
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

    public double getSpeechRate() {
        return speechRate;
    }

    public void setSpeechRate(double speechRate) {
        this.speechRate = speechRate;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public String getIdealAnswer() {
        return idealAnswer;
    }

    public void setIdealAnswer(String idealAnswer) {
        this.idealAnswer = idealAnswer;
    }

    public int getFillerWords() {
        return fillerWords;
    }

    public void setFillerWords(int fillerWords) {
        this.fillerWords = fillerWords;
    }

    public int getFluencyScore() {
        return fluencyScore;
    }

    public void setFluencyScore(int fluencyScore) {
        this.fluencyScore = fluencyScore;
    }
}
