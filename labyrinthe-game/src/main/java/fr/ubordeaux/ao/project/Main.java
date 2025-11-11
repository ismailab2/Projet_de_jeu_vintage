package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.mazing.api.WindowGame;
import fr.ubordeaux.ao.mazing.api.IWindowGame;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        IWindowGame windowGame = new WindowGame();
        windowGame.setVisible(true);
    }
}