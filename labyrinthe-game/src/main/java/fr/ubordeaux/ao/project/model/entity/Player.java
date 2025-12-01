package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.util.Point;

public class Player {
    private Point position;
    private boolean alive;

    public Player(Point position) {
        this.position = position;
        this.alive = true;
    }

    public Point getPlayerPosition() {
        return position;
    }

    public void setPlayerPosition(Point newPosition) {
        this.position = newPosition;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        System.out.println("MORT");

        this.alive = alive;
    }
}
