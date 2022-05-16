package Wordle;

import java.util.Scanner;

public interface GUI{
    Scanner userInput = new Scanner(System.in);

    static void conOut(String message){
        System.out.println(message);
    }
    static String conIn(){
        return userInput.nextLine();
    }
}
