package interviewprep.service;

import org.springframework.stereotype.Service;

@Service
public class ConfidenceService {

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

    private static final String[] UNCERTAIN_WORDS = {
            "i think",
            "maybe",
            "probably",
            "not sure",
            "i guess",
            "perhaps",
            "kind of",
            "sort of"
    };

    public int calculateConfidence(String transcript, double speechRate) {

        if (transcript == null || transcript.isBlank()) {
            return 0;
        }

        int score = 85; // Normal answer starts from 85, not 100

        int fillers = countFillerWords(transcript);

        int words = transcript.trim().split("\\s+").length;

        // ==========================
        // Filler Words
        // ==========================

        score -= fillers * 7;

        // ==========================
        // Answer Length
        // ==========================

        if(words < 10){

            score -= 35;

        }
        else if(words < 20){

            score -= 20;

        }
        else if(words < 40){

            score -= 10;

        }

        // ==========================
        // Speech Rate
        // ==========================

        if(speechRate < 90){

            score -= 20;

        }
        else if(speechRate < 110){

            score -= 10;

        }
        else if(speechRate > 180){

            score -= 10;

        }
        else if(speechRate > 200){

            score -= 20;

        }

        // ==========================
        // Repeated Words
        // ==========================

        String text = transcript.toLowerCase();

        String[] arr = text.split("\\s+");

        int repeated = 0;

        for(int i = 1; i < arr.length; i++){

            if(arr[i].equals(arr[i-1])){

                repeated++;

            }

        }

        score -= repeated * 5;

        // ==========================
        // Very Long Answer Bonus
        // ==========================

        if(words > 80){

            score += 5;

        }

        // ==========================

        if(score > 100){

            score = 100;

        }

        if(score < 0){

            score = 0;

        }

        return score;

    }

    public int countFillerWords(String transcript) {

        if (transcript == null || transcript.isBlank()) {
            return 0;
        }

        String text = transcript.toLowerCase();

        int count = 0;

        for (String filler : FILLER_WORDS) {

            String[] words = text.split("\\b" + java.util.regex.Pattern.quote(filler) + "\\b");

            count += words.length - 1;
        }

        return count;
    }

    public String getConfidenceLevel(int score) {

        if (score >= 90) {
            return "Excellent";
        }

        if (score >= 75) {
            return "Good";
        }

        if (score >= 60) {
            return "Average";
        }

        if (score >= 40) {
            return "Needs Improvement";
        }

        return "Poor";
    }

    public String getConfidenceFeedback(int score) {

        if (score >= 90) {
            return "You spoke confidently with very few filler words.";
        }

        if (score >= 75) {
            return "Good confidence. Try reducing filler words.";
        }

        if (score >= 60) {
            return "Your confidence is average. Speak more clearly and confidently.";
        }

        if (score >= 40) {
            return "Try to improve your speaking pace and reduce hesitation.";
        }

        return "Practice speaking confidently and avoid frequent pauses.";
    }

    private int countUncertainWords(String transcript) {

        if (transcript == null) {
            return 0;
        }

        String text = transcript.toLowerCase();

        int count = 0;

        for (String word : UNCERTAIN_WORDS) {

            String[] parts = text.split("\\b" + java.util.regex.Pattern.quote(word) + "\\b");

            count += parts.length - 1;
        }

        return count;
    }

}
