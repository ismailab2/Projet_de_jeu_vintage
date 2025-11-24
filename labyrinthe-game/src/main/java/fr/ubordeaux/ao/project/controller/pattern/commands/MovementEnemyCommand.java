package fr.ubordeaux.ao.project.controller.pattern.commands;

import fr.ubordeaux.ao.project.model.Game;

public class MovementEnemyCommand implements Command {

    private Game game;

    public MovementEnemyCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.moveEnemy();
    }
}

