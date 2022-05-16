package Wordle;

public class Simulation {
    public static void run(String guessFile, String answerFile) {
        Librarian librarian = new Librarian (guessFile, answerFile);

        if (librarian.getPossibleGuesses().size() > 0) {
            GUI.conOut("Enter target word:");
            String target = GUI.conIn();

            if (librarian.getPossibleAnswers().contains(target)){
                int guessCount = 0;
                for (;;) {
                    guessCount++;
                    String guess = librarian.getRecommendation();GUI.conOut("Librarian guess: " + guess);
                    String result = librarian.getGuessResult(guess, target);GUI.conOut("Guess result: " + result + "\n");

                    librarian.writeNewEdition(guess, result);
                    if (librarian.getPossibleAnswers().size() == 1) {
                        GUI.conOut("The word is: " + librarian.getPossibleAnswers().get(0));

                        if (!guess.equals(target)) {
                            guessCount++;
                        }
                        GUI.conOut("Librarian needed " + guessCount + " guesses");
                    } else {
                        continue;
                    }
                    break;
                }

            }else{GUI.conOut("Word not in file");}

        }
    }
}
