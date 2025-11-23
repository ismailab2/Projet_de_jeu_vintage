package fr.ubordeaux.ao.project.model.enums;


import java.util.Random;
//represente les 4 directions cardinals
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    private static final Random rand = new Random();

    public static Direction getRandomDirection() {
        Direction[] dirs = Direction.values();
        return dirs[rand.nextInt(dirs.length)];
    }
}

