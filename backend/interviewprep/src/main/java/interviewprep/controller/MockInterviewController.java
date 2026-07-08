package interviewprep.controller;

import interviewprep.model.*;
import interviewprep.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin(origins = "*")
public class MockInterviewController {

    private final GeminiInterviewService geminiService;
    private final InterviewQuestionService interviewQuestionService;
    private final SpeechRateService speechRateService;
    private final ConfidenceService confidenceService;
    private final InterviewReportService reportService;
    private final AnalyticsService analyticsService;

    public MockInterviewController(
            GeminiInterviewService geminiService,
            InterviewQuestionService interviewQuestionService,
            SpeechRateService speechRateService,
            ConfidenceService confidenceService,
            InterviewReportService reportService,
            AnalyticsService analyticsService) {

        this.geminiService = geminiService;
        this.interviewQuestionService = interviewQuestionService;
        this.speechRateService = speechRateService;
        this.confidenceService = confidenceService;
        this.reportService = reportService;
        this.analyticsService = analyticsService;
    }

    private final Map<String, InterviewSession> sessions = new HashMap<>();

    private static final int TOTAL_QUESTIONS = 10;

    @PostMapping("/start")
    public InterviewResponse startInterview() throws Exception {

        InterviewSession session = new InterviewSession();

        session.setSessionId(UUID.randomUUID().toString());
        session.setCurrentQuestion(1);

        session.setInterviewQuestions(
                interviewQuestionService.generateInterviewQuestions()
        );

        String firstQuestion = session.getCurrentInterviewQuestion().getQuestion();

        session.addConversation("Interviewer: " + firstQuestion);

        sessions.put(session.getSessionId(), session);

        InterviewResponse response = new InterviewResponse();

        response.setSessionId(session.getSessionId());
        response.setQuestionNumber(1);
        response.setQuestion(firstQuestion);
        response.setInterviewCompleted(false);

        return response;
    }

    @PostMapping("/answer")
    public InterviewResponse answerQuestion(
            @RequestBody AnswerRequest request
    ) throws Exception {

        InterviewSession session = sessions.get(request.getSessionId());

        if (session == null) {
            throw new RuntimeException("Interview Session Not Found.");
        }

        session.addConversation("Candidate: " + request.getAnswer());

        String lastQuestion = "";

        for (int i = session.getConversationHistory().size() - 1; i >= 0; i--) {

            String line = session.getConversationHistory().get(i);

            if (line.startsWith("Interviewer:")) {

                lastQuestion = line.replace("Interviewer:", "").trim();
                break;
            }
        }

        InterviewAnalysis analysis = geminiService.evaluateAnswer(
                lastQuestion,
                request.getAnswer()
        );


        double speechRate = speechRateService.calculateSpeechRate(
                request.getAnswer(),
                request.getDuration()
        );

        String speechFeedback = speechRateService.getSpeechFeedback(speechRate);

        int confidence = confidenceService.calculateConfidence(
                request.getAnswer(),
                speechRate
        );

        int fillerWords = analyticsService.countFillerWords(
                request.getAnswer()
        );

        int vocabulary = analyticsService.calculateVocabulary(
                request.getAnswer()
        );

        int fluency = analyticsService.calculateFluency(
                request.getAnswer(),
                speechRate
        );

        int communication = analyticsService.calculateCommunication(
                request.getAnswer(),
                speechRate,
                confidence,
                vocabulary,
                fluency
        );

        int overallScore = (int)(
            analysis.getTechnicalScore() * 0.40 +

            analysis.getGrammarScore() * 0.10 +

            communication * 0.20 +

            vocabulary * 0.10 +

            confidence * 0.10 +

            fluency * 0.10

        );

        overallScore = Math.max(0, Math.min(100, overallScore));

        InterviewResponse response = new InterviewResponse();

        response.setOverallScore(overallScore);
        response.setTechnicalScore(analysis.getTechnicalScore());
        response.setGrammarScore(analysis.getGrammarScore());
        response.setCommunicationScore(communication);
        response.setVocabularyScore(vocabulary);

        response.setStrengths(analysis.getStrengths());
        response.setWeaknesses(analysis.getWeaknesses());
        response.setSuggestions(analysis.getSuggestions());

        response.setIdealAnswer(analysis.getIdealAnswer());

        response.setSpeechRate(speechRate);
        response.setConfidenceScore(confidence);

        response.setFluencyScore(fluency);
        response.setFillerWords(fillerWords);

        InterviewResult result = new InterviewResult();

        result.setQuestionNumber(session.getCurrentQuestion());
        result.setQuestion(lastQuestion);

        result.setOverallScore(overallScore);
        result.setTechnicalScore(analysis.getTechnicalScore());
        result.setGrammarScore(analysis.getGrammarScore());
        result.setCommunicationScore(communication);
        result.setVocabularyScore(vocabulary);

        result.setConfidenceScore(confidence);
        result.setSpeechRate(speechRate);

        result.setFillerWords(fillerWords);
        result.setFluencyScore(fluency);
        result.setSpeechFeedback(speechFeedback);

        session.addResult(result);

        System.out.println(result.getSpeechFeedback());

        session.nextQuestion();

        if (session.getCurrentQuestion() > TOTAL_QUESTIONS) {

            InterviewReport report = reportService.generateReport(session);
            response.setFinalReport(report);
            response.setInterviewCompleted(true);
            sessions.remove(session.getSessionId());
            return response;
        }

        String nextQuestion = session.getCurrentInterviewQuestion().getQuestion();

        session.addConversation("Interviewer: " + nextQuestion);

        response.setQuestion(nextQuestion);
        response.setQuestionNumber(session.getCurrentQuestionIndex() + 1);
        response.setSessionId(session.getSessionId());
        response.setInterviewCompleted(false);

        return response;
    }
}