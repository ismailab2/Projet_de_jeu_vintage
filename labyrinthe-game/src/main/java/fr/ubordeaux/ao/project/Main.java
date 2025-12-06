package fr.ubordeaux.ao.project;

import fr.ubordeaux.ao.project.controller.GameController;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.view.GameFrame;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        GameFrame view = new GameFrame(game);

        game.addObserver(view);

        //la var ne sera pas utilisé mais initialise des timer, donc utile
        GameController controller = new GameController(game, view);
    }
}
