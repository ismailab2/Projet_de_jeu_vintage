package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.util.Point;
import fr.ubordeaux.ao.project.model.enums.Direction;

import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

public class Explosion {

    private final Point position;
    private final int power;
    private final Game game;
    private Timer timer;

    public Explosion(Game game, Point position, int power) {
        this.game = game;
        this.position = position;
        this.power = power;
        propagate();
    }

    private void propagate() {
        game.getGrid().getCell(position).setCellType(CellType.EXPLOSION);

        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        CellType cell = game.getGrid().getCell(position).getCellType();

        if (player.getPlayerPosition().equals(position)) {
            player.setAlive(false);
            this.game.getRulesManager().endGame();
        }

        if (enemy.getPositionEnemy().equals(position)) {
            enemy.setAlive(false);
            this.game.getRulesManager().endGame();
        }


        // Propagation dans les 4 directions
        for (Direction dir : EnumSet.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            Point dirVec = Point.directionToPoint(dir);
            for (int i = 1; i <= power; i++) {
                Point next = new Point(position.getX() + dirVec.getX() * i,
                        position.getY() + dirVec.getY() * i);

                if (!game.getGrid().validPosition(next)) break;

                Cell cellNext = game.getGrid().getCell(next);
                CellType type = cellNext.getCellType();

                if (type == CellType.WALL || type == CellType.BOX_FIXE) break;

                if (type == CellType.BOX) {
                    cellNext.setCellType(CellType.EXPLOSION);
                    game.getRulesManager().addPlayerPoint(10);
                    break;
                }

                if (type == CellType.GROUND) {
                    cellNext.setCellType(CellType.EXPLOSION);
                    System.out.println(player.getPlayerPosition());
                    System.out.println(next);

                    // atteint par le projection du bombe
                    if (player.getPlayerPosition().equals(next)) {
                        player.setAlive(false);
                        this.game.getRulesManager().endGame();
                    }

                    if (enemy.getPositionEnemy().equals(next)) {
                        enemy.setAlive(false);
                        this.game.getRulesManager().endGame();
                    }

                }
            }
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endExplosion();
            }
        }, 3000);
    }

    private void endExplosion(){
        game.getGrid().getCell(this.position).setCellType(CellType.GROUND);
        // Propagation dans les 4 directions
        for (Direction dir : EnumSet.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            Point dirVec = Point.directionToPoint(dir);
            for (int i = 1; i <= power; i++) {
                Point next = new Point(position.getX() + dirVec.getX() * i,
                        position.getY() + dirVec.getY() * i);

                if (!game.getGrid().validPosition(next)) break;

                Cell cellNext = game.getGrid().getCell(next);
                CellType type = cellNext.getCellType();

                if (type == CellType.EXPLOSION) {
                    cellNext.setCellType(CellType.GROUND);
                }
            }
        }
        game.removeExplosion(this);
        timer.cancel();
    }
}
