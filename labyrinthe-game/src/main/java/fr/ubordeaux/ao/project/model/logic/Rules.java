package fr.ubordeaux.ao.project.model.logic;

import fr.ubordeaux.ao.project.model.Game;

//implemente les regles du jeu (comment gagner/perdre, score, ...)
public class Rules {
    Game game;
    boolean isWin;
    Boolean multiPlayerMode;

    public Rules(Game game, Boolean multiPlayerMode) {
        this.isWin = false;
        this.game = game;
        this.multiPlayerMode = multiPlayerMode;
    }
}
