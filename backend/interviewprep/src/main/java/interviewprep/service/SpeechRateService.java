package interviewprep.service;

import org.springframework.stereotype.Service;

@Service
public class SpeechRateService {

    public double calculateSpeechRate(String transcript, double durationSeconds) {

        if (transcript == null || transcript.trim().isEmpty() || durationSeconds <= 0) {
            return 0;
        }

        String[] words = transcript.trim().split("\\s+");

        int totalWords = words.length;
        double minutes = durationSeconds / 60.0;

        return Math.round((totalWords / minutes) * 100.0) / 100.0;
    }

    public int getTotalWords(String transcript) {
        if (transcript == null || transcript.trim().isEmpty()) {
            return 0;
        }
        return transcript.trim().split("\\s+").length;
    }

    public String getSpeechFeedback(double speechRate) {

        if (speechRate == 0) {
            return "No speech detected.";
        }

        if (speechRate < 110) {
            return "You are speaking too slowly.";
        }

        if (speechRate <= 160) {
            return "Good speaking pace.";
        }

        if (speechRate <= 190) {
            return "You are speaking slightly fast.";
        }
        return "You are speaking too fast.";
    }
}
