package Wordle;

import java.util.Scanner;

public class WordleBot {
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        String guessFile = "WordleValidWords.txt";
        String answerFile = "WordleOfficialAnswers.txt";
        GUI.conOut("Live (1), Simulation (2) or Analysis (3)");

        switch(GUI.conIn()){
            case "1":
                Live.run(guessFile,answerFile);
                break;
            case "2":
                Simulation.run(guessFile,answerFile);
                break;
            case "3":
                Analysis.run(guessFile,answerFile);
                break;
            default:
                GUI.conOut("Incorrect Input Format");
        }
    }

}