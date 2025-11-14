package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.controller.pattern.commands.BagOfCommand;
import fr.ubordeaux.ao.project.controller.pattern.commands.MovementCommand;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {

    private Game game;
    private BagOfCommand bag;

    public GameController(Game game, BagOfCommand bag) {
        this.game = game;
        this.bag = bag;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP:
                bag.addCommand(new MovementCommand(game, Direction.NORTH));
                break;

            case KeyEvent.VK_DOWN:
                bag.addCommand(new MovementCommand(game, Direction.SOUTH));
                break;

            case KeyEvent.VK_LEFT:
                bag.addCommand(new MovementCommand(game, Direction.WEST));
                break;

            case KeyEvent.VK_RIGHT:
                bag.addCommand(new MovementCommand(game, Direction.EAST));
                break;

            case KeyEvent.VK_E:
                bag.executeAll();
                break;

            case KeyEvent.VK_C:
                bag.clear();
                break;

            case KeyEvent.VK_R:
                game = new Game();  // reset complet de la partie
                bag.clear();
                break;

            case KeyEvent.VK_Q:
                System.exit(0);
                break;
        }

        // Exécution immédiate des commandes
        bag.executeAll();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
