package fr.ubordeaux.ao.project.model.logic;

import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.entity.Bomb;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.util.Point;
import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.Direction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class PlaceBombRandom {

    private final Game game;
    private final List<Bomb> bombs;
    private final Player player;
    private final Random rand = new Random();

    public PlaceBombRandom(Game game, List<Bomb> bombs) {
        this.game = game;
        this.bombs = bombs;
        this.player = game.getPlayer();
    }

    public void placeBombRandom(int power) {
        List<Point> accessible = new ArrayList<>();
        Point playerPos = player.getPlayerPosition();

        for (Direction dir : EnumSet.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            Point newPos = Point.sum(playerPos, Point.directionToPoint(dir));
            Cell cell = game.getGrid().getCell(newPos);
            if (cell.getCellType() == CellType.GROUND) {
                accessible.add(newPos);
            }
        }

        if (!accessible.isEmpty()) {
            Point bombPos = accessible.get(rand.nextInt(accessible.size()));
            Bomb bomb = new Bomb(game, bombPos, power);
            bombs.add(bomb);
            bomb.place();
        }
    }
}
