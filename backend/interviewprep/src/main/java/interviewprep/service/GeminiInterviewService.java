package interviewprep.service;

import interviewprep.model.InterviewAnalysis;
import interviewprep.model.InterviewSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class GeminiInterviewService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper mapper = new ObjectMapper();

    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

public String getNextQuestion(InterviewSession session) throws Exception {

    StringBuilder history = new StringBuilder();

    for(String s : session.getConversationHistory()){
        history.append(s).append("\n");
    }

    String prompt = """
    You are an experienced Software Engineering interviewer.
    
    Conduct ONE complete interview.
    
    Rules:
    
    1. Ask EXACTLY 10 questions.
    
    2. Ask ONLY ONE question at a time.
    
    3. Remember previous conversation.
    
    4. Never repeat a question.
    
    5. First question must always be:
    
    Tell me about yourself.
    
    6. Then continue naturally.
    
    7. Include:
    
    - Java
    - OOP
    - Collections
    - Spring Boot
    - SQL
    - Projects
    - HR
    
    8. If candidate answers well,
    increase difficulty.
    
    9. If candidate answers poorly,
    ask easier follow-up.
    
    10. Return ONLY next question.
    
    Current Question Number:
    
    %s
    
    Conversation:
    
    %s
    
        """
    .formatted(
            session.getCurrentQuestion(),
            history.toString()
    );

    String requestBody =
    """
    {
      "contents":[
        {
          "parts":[
            {
              "text":%s
            }
          ]
        }
      ]
    }
    """.formatted(
            mapper.writeValueAsString(prompt)
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);

    String response = restTemplate.postForObject(
        URL + apiKey,
        entity,
        String.class
    );

    JsonNode root = mapper.readTree(response);

    return root
            .get("candidates")
            .get(0)
            .get("content")
            .get("parts")
            .get(0)
            .get("text")
            .asText()
            .trim();

    }

    public InterviewAnalysis evaluateAnswer(String question,String answer) throws Exception {

        String prompt = """
        You are an experienced Software Engineering interviewer evaluating a fresher candidate.
        
        Interview Question:
        %s
        
        Candidate Answer:
        %s
        
        Evaluate the answer fairly like a real interviewer.
        
        ========================
        SCORING RULES
        ========================
        
        Technical Score (0-100)
        - Judge only technical correctness.
        - Ignore grammar while giving technical score.
        
        Grammar Score (0-100)
        - Judge grammar only.
        - Minor mistakes should not reduce the score much.
        - If the English is understandable with only minor mistakes,
        Grammar Score should normally be between 80 and 90.
        
        Only give below 70 when grammar makes the answer difficult to understand.
        
        Communication Score (0-100)
        - Judge clarity.
        - Judge confidence.
        - Judge logical flow.
        - Ignore small grammar mistakes.
        
        Vocabulary Score (0-100)
        - Judge professional vocabulary.
        - Do NOT expect advanced English from freshers.
        - If technical terms are used correctly, vocabulary should usually be above 75.
        
        Overall Score (0-100)
        
        Score Guidelines
        
        Excellent Answer
        90-100
        
        Good Answer
        80-89
        
        Average Answer
        65-79
        
        Weak Answer
        45-64
        
        Poor Answer
        Below 45
        
        A reasonable fresher answer should normally score between 70 and 85.
        
        Do NOT give unnecessarily low scores.
        
        ========================
        FEEDBACK RULES
        ========================
        
        Return exactly:
        
        - 3 strengths
        - 3 weaknesses
        - 3 suggestions
        
        Rules:
        
        - Mention positives first.
        - If the answer is good, appreciate it.
        - The FIRST strength must appreciate the answer.
        - Never invent mistakes.
        - Weaknesses must be constructive.
        - Suggestions must be practical.
        - One sentence per suggestion.
        - Maximum 20 words per suggestion.
        
        ========================
        IDEAL ANSWER
        ========================
        
        Write a better version of the candidate's answer.
        
        Rules:
        
        - Maximum 60 words.
        - Maximum 5 sentences.
        - Simple English.
        - Natural speaking style.
        - Interview-ready.
        - Do NOT use bullet points.
        - Do NOT write textbook definitions.
        
        Return ONLY valid JSON.
        
        {
          "overallScore":0,
          "technicalScore":0,
          "grammarScore":0,
          "communicationScore":0,
          "vocabularyScore":0,
          "strengths":["","",""],
          "weaknesses":["","",""],
          "suggestions":["","",""],
          "idealAnswer":""
        }
        """.formatted(question, answer);

        String requestBody =
        """
        {
          "contents":[
            {
              "parts":[
                {
                  "text":%s
                }
              ]
            }
          ]
        }
        """.formatted(
                mapper.writeValueAsString(prompt)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {

        String response = restTemplate.postForObject(
            URL + apiKey,
            entity,
            String.class
        );

        JsonNode root = mapper.readTree(response);

            String json = root
                .get("candidates")
                .get(0)
                .get("content")
                .get("parts")
                .get(0)
                .get("text")
                .asText()
                .replace("```json", "")
                .replace("```", "")
                .trim();

            return mapper.readValue(
                json,
                interviewprep.model.InterviewAnalysis.class
            );

        }
        catch (Exception e){
            interviewprep.model.InterviewAnalysis analysis = new interviewprep.model.InterviewAnalysis();

            analysis.setOverallScore(0);
            analysis.setTechnicalScore(0);
            analysis.setGrammarScore(0);
            analysis.setCommunicationScore(0);
            analysis.setVocabularyScore(0);

            analysis.setStrengths(java.util.List.of(
                    "Unable to evaluate answer.",
                    "",
                    ""
                )
            );

            analysis.setWeaknesses(java.util.List.of(
                    "Gemini API unavailable.",
                    "",
                    ""
                )
            );

            analysis.setSuggestions(java.util.List.of(
                    "Try again later.",
                    "Check API quota.",
                    "Verify API key."
                )
            );

            analysis.setIdealAnswer("");

            return analysis;

        }

    }

}