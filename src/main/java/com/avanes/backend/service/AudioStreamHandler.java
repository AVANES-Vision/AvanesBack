package com.avanes.backend.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.vosk.Recognizer;
import org.vosk.Model;
import org.vosk.LibVosk;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;



@Component
public class AudioStreamHandler extends BinaryWebSocketHandler {

    //    private static final String MODEL_PATH = "C:\\Users\\Kasun\\Downloads\\vosk-model-small-en-us-0.15\\vosk-model-small-en-us-0.15";
    private static final String MODEL_PATH;
    private Recognizer recognizer;
    private boolean recognizing = false;

    static {
        try {
            MODEL_PATH = Paths.get(AudioStreamHandler.class.getClassLoader().getResource("vosk-model-small-en-us-0.15").toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public AudioStreamHandler() throws IOException {
        Model model = new Model(MODEL_PATH);
        recognizer = new Recognizer(model, 16000);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        try {
            byte[] payload = message.getPayload().array();

            if (recognizer.acceptWaveForm(payload, payload.length)) {
                String result = recognizer.getResult();
                // Extract the recognized text from the result
                String recognizedText = extractRecognizedText(result);

                // Check if the recognized text contains any of the target numbers or their word representations
                String detectedNumber = containsTargetNumber(recognizedText);
                if (detectedNumber != null) {
                    // Send a response back to the client
                    String response = "Detected number: " + detectedNumber;
                    session.sendMessage(new BinaryMessage(response.getBytes()));
                    System.out.println(response); // Log to console
                } else {
                    // Send a default response if no target number is detected
                    session.sendMessage(new BinaryMessage("No target number detected.".getBytes()));
                }
            } else {
                // Send partial results back to the client
                String partialResult = recognizer.getPartialResult();
                session.sendMessage(new BinaryMessage(partialResult.getBytes()));
            }
        } catch (Exception e) {
            // Log any exceptions
            System.err.println("Error processing audio message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Extracts the recognized text from the Vosk recognizer result.
     */
    private String extractRecognizedText(String result) {
        System.out.println("Recognizer result: " + result); // Debug statement

        // The result is a JSON string, e.g., {"text": "one two three"}
        // Extract the "text" field
        int startIndex = result.indexOf("\"text\" : \"") + 10; // Start of the text value
        int endIndex = result.indexOf("\"", startIndex); // End of the text value

        if (startIndex == -1 || endIndex == -1) {
            System.err.println("Error extracting text from result: " + result);
            return "";
        }

        String recognizedText = result.substring(startIndex, endIndex).toLowerCase(); // Convert to lowercase for easier matching
        System.out.println("Extracted recognized text: " + recognizedText); // Debug statement

        return recognizedText;
    }

    /**
     * Checks if the recognized text contains any of the target numbers or their word representations.
     */
    private String containsTargetNumber(String recognizedText) {
        // Define a wordlist for each target number
        String[] numberOne = {"1", "one", "won"};
        String[] numberTwo = {"2", "two", "to", "too", "who", "though", "do"};
        String[] numberThree = {"3", "three", "tree"};
        String[] numberFour = {"4", "four", "for", "fore", "whoa", "whole", "hall", "oh", "fall"};
        String[] numberFive = {"5", "five", "while", "file", "vile", "why", "high", "hive", "fire"};

        // Define wordlists for additional commands
        String[] nextCommand = {"next", "nex", "nxt", "nest"};
        String[] previousCommand = {"previous", "prev", "back"};
        String[] finishCommand = {"finish", "end", "complete"};

        // Combine all wordlists into a single list
        String[][] wordlists = {numberOne, numberTwo, numberThree, numberFour, numberFive, nextCommand, previousCommand, finishCommand};
        String[] commands = {"1", "2", "3", "4", "5", "next", "previous", "finish"};

        // Check if the recognized text contains any of the words
        for (int i = 0; i < wordlists.length; i++) {
            for (String word : wordlists[i]) {
                if (recognizedText.contains(word)) {
                    return commands[i];
                }
            }
        }

        return null;
    }

    public void stopRecognition() {
        recognizing = false;
    }
}