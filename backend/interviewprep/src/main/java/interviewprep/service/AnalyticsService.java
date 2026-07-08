package interviewprep.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AnalyticsService {

    private static final String[] FILLER_WORDS = {
            "um",
            "uh",
            "like",
            "actually",
            "basically",
            "you know",
            "okay",
            "hmm"
    };

    // Count Filler Words

    public int countFillerWords(String transcript){
        if(transcript == null || transcript.isBlank()){
            return 0;
        }

        String text = transcript.toLowerCase();

        int count = 0;

        for(String filler : FILLER_WORDS){
            String[] arr =
                    text.split("\\b" +
                            java.util.regex.Pattern.quote(filler)
                            + "\\b");
            count += arr.length - 1;
        }
        return count;

    }

    // Vocabulary Score

    public int calculateVocabulary(String transcript) {

        if (transcript == null || transcript.isBlank()) {
            return 0;
        }

        String[] words = transcript
                .toLowerCase()
                .replaceAll("[^a-z ]", "")
                .split("\\s+");

        Set<String> uniqueWords = new HashSet<>();

        int longWords = 0;

        for (String word : words) {

            if (word.length() > 2) {
                uniqueWords.add(word);
            }

            if (word.length() >= 7) {
                longWords++;
            }
        }

        int totalWords = words.length;

        double diversity =
                (double) uniqueWords.size() / totalWords;

        int score = 50;

        // Lexical Diversity (0-30)

        score += (int) (diversity * 30);

        // Answer Length (0-20)

        if (totalWords >= 25) score += 10;

        if (totalWords >= 50) score += 10;

        // Professional Vocabulary (0-20)

        score += Math.min(longWords, 10) * 2;

        // Very Short Answer Penalty

        if (totalWords < 10) score -= 20;

        // Repeated Words Penalty

        if (diversity < 0.45) score -= 10;

        if (score > 100) score = 100;

        if (score < 0) score = 0;

        return score;
    }

    // Fluency Score

    public int calculateFluency(String transcript,
                                double speechRate){

        int score = 100;

        int fillers = countFillerWords(transcript);

        score -= fillers * 6;

        if(speechRate < 100){
            score -= 15;
        }

        if(speechRate > 180){
            score -= 15;
        }

        if(score < 0){
            score = 0;
        }

        return score;

    }

    // Communication Score

    public int calculateCommunication(String transcript,
                                      double speechRate,
                                      int confidence,
                                      int vocabulary,
                                      int fluency) {

        if (transcript == null || transcript.isBlank()) {
            return 0;
        }

        int score = 0;

        String[] words = transcript.trim().split("\\s+");

        int totalWords = words.length;

        // Confidence (35 Marks)

        score += (int)(confidence * 0.35);

        // Vocabulary (25 Marks)

        score += (int)(vocabulary * 0.25);

        // Fluency (20 Marks)

        score += (int)(fluency * 0.20);

        // Speech Rate (10 Marks)

        if(speechRate >= 110 && speechRate <= 160){
            score += 10;
        }
        else if(speechRate >= 90 && speechRate < 110){
            score += 7;
        }
        else if(speechRate > 160 && speechRate <= 180){
            score += 7;
        }
        else{
            score += 3;
        }

        // Answer Length (10 Marks)

        if(totalWords >= 40){
            score += 10;
        }
        else if(totalWords >= 25){
            score += 8;
        }
        else if(totalWords >= 15){
            score += 5;
        }
        else{
            score += 2;
        }

        if(score > 100) score = 100;

        if(score < 0) score = 0;

        return score;

    }

    public String getOverallFeedback(int score){

        if(score >= 90){
            return "Outstanding interview performance. You demonstrated excellent technical knowledge and communication skills.";
        }

        if(score >= 80){
            return "Very good performance. With a little more confidence and practice, you are interview ready.";
        }

        if(score >= 70){
            return "Good performance. Strengthen a few technical concepts and improve communication for better results.";
        }

        if(score >= 60){
            return "Average performance. More practice is needed before attending interviews.";
        }

        return "Needs significant improvement. Focus on technical fundamentals and communication skills.";

    }

    public String getInterviewLevel(int score){

        if(score >= 90){
            return "Excellent";
        }

        if(score >= 80){
            return "Good";
        }

        if(score >= 70){
            return "Average";
        }

        if(score >= 60){
            return "Needs Improvement";
        }

        return "Poor";
    }
}