package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.project.controller.pattern.commands.BagOfCommand;
import fr.ubordeaux.ao.project.controller.pattern.commands.MovementCommand;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        /*
        //use for model debug
        //boucle infini pour un affichage du modele dans le terminal avec controle clavier simple
        //on le supprimera pour le rendu final, avec le "throws IOException" et le "import java.io.IOException;"
        Game game = new Game(); //create default game
        while (true) {
            if (System.in.available() > 0) {
                char input = (char) System.in.read();

                switch (input) {
                    case 'z' -> game.movePlayer(Direction.NORTH);
                    case 's' -> game.movePlayer(Direction.SOUTH);
                    case 'q' -> game.movePlayer(Direction.WEST);
                    case 'd' -> game.movePlayer(Direction.EAST);
                }
                game.printGame();
            }
        }


         */


        Game game = new Game();
        BagOfCommand bag = new BagOfCommand();


        bag.addCommand(new MovementCommand(game, Direction.NORTH));
        bag.executeAll();
        game.printGame();

        bag.addCommand(new MovementCommand(game, Direction.SOUTH));
        bag.executeAll();
        game.printGame();


        bag.addCommand(new MovementCommand(game, Direction.EAST));
        bag.executeAll();
        game.printGame();


        bag.addCommand(new MovementCommand(game, Direction.WEST));
        bag.executeAll();
        game.printGame();

        // Avant exécution
        System.out.println(game.getPlayer().getPlayerPosition()); // (1,1)

        //bag.executeAll();

        //Après exécution
        System.out.println(game.getPlayer().getPlayerPosition());

    }
}