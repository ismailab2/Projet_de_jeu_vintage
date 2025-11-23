package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.util.Point;

import java.util.stream.DoubleStream;

public class Enemy {

    private Point PositionEnemy;
    private boolean alive;

    public Enemy(Point positionEnemy){
        this.PositionEnemy = positionEnemy;
        this.alive = true;
    }

    public Point getPositionEnemy() {
            return PositionEnemy;
    }

    public void setPositionEnemy(Point positionEnemy) {
        PositionEnemy = positionEnemy;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
