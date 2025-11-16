package fr.ubordeaux.ao.project.controller.pattern.commands;

import fr.ubordeaux.ao.project.model.Game;

public class PlaceBombCommand implements Command {

    private final Game game;
    private final int power;


    public PlaceBombCommand(Game game, int power) {
        this.game = game;
        this.power = power;
    }

    @Override
    public void execute() {
        game.placeBomb(power);
    }
}
