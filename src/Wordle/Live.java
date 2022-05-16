package Wordle;

public class Live {
    public static void run(String guessFile, String answerFile) {
        Librarian librarian = new Librarian(guessFile,answerFile);

        if (librarian.getPossibleAnswers().size() > 0) {
            for (;;) {
                String guess = librarian.getRecommendation();
                GUI.conOut("Recommended guess: " + guess);
                String result = GUI.conIn();
                librarian.writeNewEdition(guess, result);

                switch (librarian.getPossibleAnswers().size()) {
                    case 0:
                        GUI.conOut(("Check your answers! You made a mistake somewhere"));
                        break;
                    case 1:
                        GUI.conOut("The word is: " + librarian.getPossibleAnswers().get(0));
                        break;
                    default:
                        continue;
                }
                break;
            }
        }
    }
}
