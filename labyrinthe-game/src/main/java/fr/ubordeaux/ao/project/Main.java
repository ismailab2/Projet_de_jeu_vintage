package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.mazing.api.WindowGame;
import fr.ubordeaux.ao.mazing.api.IWindowGame;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        //use for model debug
        Game game = new Game(); //create default game
        game.printGame(); //print the current game

        //test un movement dans une direction invalide (un mur)
        game.movePlayer(Direction.WEST);
        game.printGame();

        //test un movement dans une direction valide (un ground)
        game.movePlayer(Direction.SOUTH);
        game.printGame();
    }
}