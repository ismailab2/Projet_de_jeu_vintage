package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.util.Point;
import fr.ubordeaux.ao.project.model.enums.Direction;

import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

//cette class modelise une explosion de la game (qui était auparavant une bomb)
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

    //l'explosion de propage sur le labyrinth, puis lance un timer pour sa disparation
    private void propagate() {
        game.getGrid().getCell(position).setCellType(CellType.EXPLOSION);

        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        CellType cell = game.getGrid().getCell(position).getCellType();

        if (player.getPlayerPosition().equals(position)) {
            game.killPlayer();

        }

        if (enemy.getPositionEnemy().equals(position)) {
            game.killEnnemy();

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

                    // joueur atteint par le projection du bombe
                    if (player.getPlayerPosition().equals(next)) {
                        game.killPlayer();
                    }

                    // ennemie atteint par le projection du bombe
                    if (enemy.getPositionEnemy().equals(next)) {
                        game.killEnnemy();
                    }
                }
            }
        }
        this.game.notifyExplosion();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endExplosion();
            }
        }, 1000);
    }

    //l'explosion est fini, on met a jour les case concerné par sa propagation puis l'enleve de la game
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

        this.game.getPlayer().getBackBomb();

        game.notifyExplosion();
    }
}
