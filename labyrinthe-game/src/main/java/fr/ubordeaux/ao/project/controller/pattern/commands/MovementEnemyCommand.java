package fr.ubordeaux.ao.project.controller.pattern.commands;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;

public class MovementEnemyCommand implements Command {

    private Game game;

    public MovementEnemyCommand(Game game, Direction direction) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.moveEnemy();
    }
}

