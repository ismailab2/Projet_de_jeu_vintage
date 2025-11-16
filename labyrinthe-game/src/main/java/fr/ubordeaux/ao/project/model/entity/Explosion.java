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
    private boolean finished;

    public Explosion(Game game, Point position, int power) {
        this.game = game;
        this.position = position;
        this.power = power;
        this.finished = false;
        propagate();
    }

    private void propagate() {
        // Case centrale
        game.getGrid().getCell(position).setCellType(CellType.EXPLOSION);

        for (Direction dirEnum : EnumSet.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            Point dir = Point.directionToPoint(dirEnum);

            for (int i = 1; i <= power; i++) {
                Point next = new Point(position.getX() + dir.getX() * i,
                        position.getY() + dir.getY() * i);

                if (!game.getGrid().validPosition(next)) break;

                Cell cell = game.getGrid().getCell(next);
                CellType type = cell.getCellType();

                if (type == CellType.WALL || type == CellType.BOX_FIXE) break;

                if (type == CellType.BOX) {
                    cell.setCellType(CellType.GROUND);
                    break;
                }

                cell.setCellType(CellType.EXPLOSION);
            }
        }
    }

    public void update() {
        if (!finished) {
            clearExplosion();
            finished = true;
        }
    }

    private void clearExplosion() {
        game.getGrid().getCell(position).setCellType(CellType.GROUND);

        for (Direction dirEnum : EnumSet.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            Point next = Point.sum(position, Point.directionToPoint(dirEnum));
            if (game.getGrid().validPosition(next)) {
                Cell cell = game.getGrid().getCell(next);
                if (cell.getCellType() == CellType.EXPLOSION) {
                    if (cell.getOriginalType() == CellType.GROUND) {
                        cell.setCellType(CellType.VIDE);
                    } else {
                        cell.setCellType(cell.getOriginalType());
                    }
                }
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
