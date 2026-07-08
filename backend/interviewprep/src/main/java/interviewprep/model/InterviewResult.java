package interviewprep.model;

public class InterviewResult {

    private int questionNumber;
    private String question;

    private int overallScore;
    private int technicalScore;
    private int grammarScore;
    private int communicationScore;
    private int vocabularyScore;

    private int confidenceScore;
    private double speechRate;

    private int fillerWords;
    private int fluencyScore;

    private String speechFeedback;

    public InterviewResult() {
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

    public String getSpeechFeedback() {
        return speechFeedback;
    }

    public void setSpeechFeedback(String speechFeedback) {
        this.speechFeedback = speechFeedback;
    }
}