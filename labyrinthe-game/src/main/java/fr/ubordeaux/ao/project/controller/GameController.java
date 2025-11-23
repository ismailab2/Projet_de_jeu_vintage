package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.controller.pattern.commands.BagOfCommand;
import fr.ubordeaux.ao.project.controller.pattern.commands.MovementPlayerCommand;
import fr.ubordeaux.ao.project.controller.pattern.commands.PlaceBombCommand;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//public  static final int POWER = 2;


public class GameController implements KeyListener {

    private Game game;
    private final BagOfCommand bag;

    public GameController(Game game, BagOfCommand bag) {
        this.game = game;
        this.bag = bag;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP ->
                    bag.addCommand(new MovementPlayerCommand(game, Direction.NORTH));

            case KeyEvent.VK_DOWN ->
                    bag.addCommand(new MovementPlayerCommand(game, Direction.SOUTH));

            case KeyEvent.VK_LEFT ->
                    bag.addCommand(new MovementPlayerCommand(game, Direction.WEST));

            case KeyEvent.VK_RIGHT ->
                    bag.addCommand(new MovementPlayerCommand(game, Direction.EAST));

            case KeyEvent.VK_SPACE ->
                    bag.addCommand(new PlaceBombCommand(game, GameConstants.POWER));

            case KeyEvent.VK_E ->
                    bag.executeAll();

            case KeyEvent.VK_C ->
                    bag.clear();

            case KeyEvent.VK_R -> {
                game = new Game();
                bag.clear();
            }

            case KeyEvent.VK_Q ->
                    System.exit(0);
        }

        bag.executeAll();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static final class GameConstants {
        public static final int POWER = 2;
    }

}
