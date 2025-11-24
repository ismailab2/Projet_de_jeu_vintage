package fr.ubordeaux.ao.project.controller;

import fr.ubordeaux.ao.project.controller.pattern.commands.*;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.view.GameFrame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;




public class GameController {

    private final Game game;
    private final BagOfCommand playerBag;
    private final BagOfCommand enemyBag;
    private final GameFrame view;

    public GameController(Game game, GameFrame view) {
        this.game = game;
        this.view = view;

        this.playerBag = new BagOfCommand();
        this.enemyBag = new BagOfCommand();

        startTimers();
    }

    public KeyAdapter getKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP    -> playerBag.addCommand(new MovementPlayerCommand(game, Direction.NORTH));
                    case KeyEvent.VK_DOWN  -> playerBag.addCommand(new MovementPlayerCommand(game, Direction.SOUTH));
                    case KeyEvent.VK_LEFT  -> playerBag.addCommand(new MovementPlayerCommand(game, Direction.WEST));
                    case KeyEvent.VK_RIGHT -> playerBag.addCommand(new MovementPlayerCommand(game, Direction.EAST));
                    case KeyEvent.VK_SPACE -> playerBag.addCommand(new PlaceBombCommand(game, 1));
                }
                playerBag.executeAll();
            }
        };
    }

    private void startTimers() {

        new Timer(500, e -> {
            enemyBag.addCommand(new MovementEnemyCommand(game));
            enemyBag.executeAll();
        }).start();

        new Timer(50, e -> view.repaint()).start();
    }
}
