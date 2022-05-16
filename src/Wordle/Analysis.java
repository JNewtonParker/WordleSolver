package Wordle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis {
    public static void run(String guessFile, String answerFile) {
        double startTime  = System.currentTimeMillis();
        Map<String, Integer> data = new HashMap<String,Integer>();
        Map<String, String> historyBook = new HashMap<String,String>();

        List<String> answersToCheck = new ArrayList<>();
        try {
            answersToCheck = Files.readAllLines(Path.of(answerFile));
            GUI.conOut("Analysing...");
        } catch (IOException e) {
            GUI.conOut("One of the files couldn't be found");
        }


        for (String target: answersToCheck){

            Librarian librarian = new Librarian(guessFile, answerFile);
            int guessCount = 0;
            String guess;String result;
            String history = "";
            for (; ; ) {

                if (historyBook.containsKey(history)) {//if the current path has already happened...
                    guess = historyBook.get(history);//...next guess is what happened last time and
                    librarian.addTakenLetters(guess); //add the used letters to TakenLetters in techDict
                }else{
                    guess = librarian.getRecommendation(); //otherwise, make the next guess
                    historyBook.put(history,guess);// and add the new guess as the result of the current path
                }

                guessCount++;//increment guess count
                result = librarian.getGuessResult(guess, target);//find result for new guess
                history += (guess+result); //update path for current history
                librarian.writeNewEdition(guess, result); //modify possible answer list

                if (librarian.getPossibleAnswers().size() == 1) {
                    if (!guess.equals(target)) {
                        guessCount++;
                    }
                } else {

                    continue;
                }
                break; //if they guessed the right answer break target loop


            }
            historyBook.put(history,target);
            data.put(target, guessCount);

        }
        double endTime  = System.currentTimeMillis();
        GUI.conOut(displayResults(answersToCheck, data, endTime-startTime));
    }

    private static String displayResults(List<String> answersChecked, Map<String, Integer> data, double timeElapsed) {
        String output = "\n------------------------------\n";
        String maxWord = answersChecked.get(0);
        int maxValue = data.get(maxWord);

        DecimalFormat df = new DecimalFormat("#.###");

        Map<Integer,Integer> guessBuckets = new HashMap<Integer,Integer>();

        double failed = 0;
        double avgNumGuesses = 0;
        for (String word:answersChecked){
            if (data.get(word) > maxValue){
                maxValue = data.get(word);
                maxWord = word;}

            if (guessBuckets.containsKey(data.get(word))){
                guessBuckets.put(data.get(word),guessBuckets.get(data.get(word))+1);
            }else{
                guessBuckets.put(data.get(word),1);
            }
            avgNumGuesses += data.get(word);
        }

        avgNumGuesses = avgNumGuesses/answersChecked.size();
        double failedPercentage = (failed/answersChecked.size())*100;

        output += "Longest Path: " + maxWord + ", " + maxValue + "\n";
        output += "Pass Rate: " + df.format(100-failedPercentage) + "%\n";
        output += "\n";
        output += "Avg. Guesses: " + df.format(avgNumGuesses) + "\n";
        output += "Time Elapsed: " + (timeElapsed)/1000 + " seconds.\n";
        output += "------------------------------\n";

        for (Integer bucket:guessBuckets.keySet()){
            output+= bucket + " Guesses: " + guessBuckets.get(bucket) + ". ";
        }

        output += "\n------------------------------\n";
        return output;
    }
}
