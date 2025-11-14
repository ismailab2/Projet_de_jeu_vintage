package fr.ubordeaux.ao.project.controller.pattern.commands;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;

public class MovementCommand implements Command {

    private Game game;
    private Direction direction;

    public MovementCommand(Game game, Direction direction) {
        this.game = game;
        this.direction = direction;
    }

    @Override
    public void execute() {
        game.movePlayer(direction);
    }
}

