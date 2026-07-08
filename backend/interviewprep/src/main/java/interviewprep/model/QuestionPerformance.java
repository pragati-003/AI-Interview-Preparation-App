package interviewprep.model;

public class QuestionPerformance {

    private int questionNumber;

    private int overallScore;

    private String status;

    public QuestionPerformance() {
    }

    public QuestionPerformance(int questionNumber,
                               int overallScore,
                               String status) {

        this.questionNumber = questionNumber;
        this.overallScore = overallScore;
        this.status = status;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}