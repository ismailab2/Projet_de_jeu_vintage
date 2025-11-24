package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.util.Point;

public class Player {
    private Point position;
    private boolean alive;
    private int score;

    public Player(Point position) {
        this.position = position;
        this.alive = true;
        this.score = 0;
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
        this.alive = alive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
