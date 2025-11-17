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
    private Explosion explosion;
    private Timer timer;

    public Bomb(Game game, Point position, int power) {
        this.game = game;
        this.position = position;
        this.power = power;
        this.exploded = false;
    }

    public void place() {
        game.getGrid().getCell(position).setCellType(CellType.BOMB);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                explode();
            }
        }, 3000);
    }

    private void explode() {
        if (exploded) return;

        exploded = true;

        explosion = new Explosion(game, position, power);

        //game.addExplosion(explosion);

        // Remet la case centrale de la bombe comme explosion
        //game.getGrid().getCell(position).setCellType(CellType.EXPLOSION);

        timer.cancel();

        //game.removeBombe(this);
    }

    public void update() {
        if (explosion != null) {
            explosion.update();
        }
    }

    public boolean hasExploded() {
        return exploded;
    }

    public boolean isExplosionFinished() {
        return explosion != null && explosion.isFinished();
    }

    public Point getPosition() {
        return position;
    }
}
