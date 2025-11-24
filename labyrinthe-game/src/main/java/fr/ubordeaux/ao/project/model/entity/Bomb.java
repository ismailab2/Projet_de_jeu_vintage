package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.util.Point;



import java.util.Timer;
import java.util.TimerTask;

public class Bomb {

    private final Point position;
    private final int power;
    private final Game game;
    private boolean exploded;
    private Timer timer;

    public Bomb(Game game, Point position, int power) {
        this.game = game;
        this.position = position;
        this.power = power;
        this.exploded = false;

        this.place();
    }

    public void place() {
        if (!game.getCollision().canPlaceBomb(game.getPlayer().getPlayerPosition())) {
            System.out.println("Impossible de poser la bombe ici !");
            return;
        }

        game.getGrid().getCell(position).setCellType(CellType.BOMB);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                explode();
            }
        }, 3000);
    }

    public void explode() {
        if (exploded) return;

        exploded = true;

        Explosion explosion = new Explosion(game, position, power);

        game.addExplosion(explosion);

        game.removeBombe(this);
        timer.cancel();
    }

    public boolean isExploded() {
        return exploded;
    }

}
