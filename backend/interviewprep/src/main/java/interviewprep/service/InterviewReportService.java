package interviewprep.service;

import interviewprep.model.InterviewReport;
import interviewprep.model.InterviewResult;
import interviewprep.model.InterviewSession;
import org.springframework.stereotype.Service;

import interviewprep.model.QuestionPerformance;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewReportService {

    public InterviewReport generateReport(InterviewSession session) {

        InterviewReport report = new InterviewReport();

        int overall = 0;
        int technical = 0;
        int grammar = 0;
        int communication = 0;
        int vocabulary = 0;
        int confidence = 0;
        int fluency = 0;
        int fillerWords = 0;
        double speechRate = 0;
        String speechFeedback = "";

        for (InterviewResult result : session.getResults()) {

            overall += result.getOverallScore();
            technical += result.getTechnicalScore();
            grammar += result.getGrammarScore();
            communication += result.getCommunicationScore();
            vocabulary += result.getVocabularyScore();
            confidence += result.getConfidenceScore();
            fluency += result.getFluencyScore();
            fillerWords += result.getFillerWords();
            speechRate += result.getSpeechRate();
            speechFeedback = result.getSpeechFeedback();
        }

        int total = session.getResults().size();

        if (total > 0) {

            report.setOverallScore(overall / total);
            report.setTechnicalScore(technical / total);
            report.setGrammarScore(grammar / total);
            report.setCommunicationScore(communication / total);
            report.setVocabularyScore(vocabulary / total);
            report.setConfidenceScore(confidence / total);
            report.setSpeechRate(speechRate / total);
            report.setFluencyScore(fluency / total);
            report.setAverageFillerWords(fillerWords / total);

        }

        report.setSpeechFeedback(speechFeedback);

        report.setQuestionsAttempted(total);


        /* Strong Areas */

        List<String> strongAreas = new ArrayList<>();

        if(report.getTechnicalScore() >= 80)
            strongAreas.add("Strong Technical Knowledge");

        if(report.getCommunicationScore() >= 80)
            strongAreas.add("Good Communication Skills");

        if(report.getVocabularyScore() >= 80)
            strongAreas.add("Professional Vocabulary");

        if(report.getConfidenceScore() >= 80)
            strongAreas.add("High Confidence");

        report.setStrongAreas(strongAreas);

        /* Improvement Areas */

        List<String> improvementAreas = new ArrayList<>();

        if(report.getTechnicalScore() < 70)
            improvementAreas.add("Improve Technical Concepts");

        if(report.getGrammarScore() < 70)
            improvementAreas.add("Improve Grammar");

        if(report.getCommunicationScore() < 70)
            improvementAreas.add("Improve Communication");

        if(report.getVocabularyScore() < 70)
            improvementAreas.add("Improve Vocabulary");

        report.setImprovementAreas(improvementAreas);

        /* AI Summary */

        String summary;

        if(report.getOverallScore() >= 85){
            summary ="Excellent interview performance. You demonstrated strong technical knowledge, communicated confidently, and answered questions in a structured manner.";
        }
        else if(report.getOverallScore() >= 70){
            summary ="Good overall interview performance. Your technical knowledge is solid, but improving communication and grammar will make your answers stronger.";
        }
        else{
            summary ="You have a good foundation, but more practice is required to improve confidence, communication and technical explanations.";
        }

        report.setAiSummary(summary);

        /* Question Status */

        List<QuestionPerformance> performances = new ArrayList<>();

        for(InterviewResult result : session.getResults()){

            String status;
            if(result.getOverallScore() >= 85){
                status = "Excellent";
            }
            else if(result.getOverallScore() >= 70){
                status = "Good";
            }
            else{
                status = "Needs Improvement";
            }

            performances.add(
                new QuestionPerformance(
                    result.getQuestionNumber(),
                    result.getOverallScore(),
                    status
                )
            );
        }

        report.setQuestionResults(performances);
        return report;
    }
}