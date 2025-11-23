package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.util.Point;
import fr.ubordeaux.ao.project.model.enums.Direction;

import java.util.EnumSet;

public class Explosion {

    private final Point position;
    private final int power;
    private final Game game;

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

        if (cell == CellType.EXPLOSION) {

            if (player.getPlayerPosition().equals(position)) {
                player.setAlive(false);
            }

            if (enemy.getPositionEnemy().equals(position)) {
                enemy.setAlive(false);
            }
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
                    game.getPlayer().setScore(game.getPlayer().getScore() + 10);
                    break;
                }

                if (type == CellType.GROUND) {
                    cellNext.setCellType(CellType.EXPLOSION);

                    // atteint par le projection du bombe
                    if (player.getPlayerPosition().equals(next)) {
                        player.setAlive(false);
                    }

                    if (enemy.getPositionEnemy().equals(next)) {
                        enemy.setAlive(false);
                    }

                }
            }
        }
    }



    public Point getPosition() {
        return position;
    }

    public int getPower() {
        return power;
    }
}
