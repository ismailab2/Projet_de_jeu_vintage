package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.util.Point;

public class Enemy {

    private Point PositionEnemy;

    public Enemy(Point positionEnemy){
        this.PositionEnemy = positionEnemy;
    }

    public Point getPositionEnemy() {
            return PositionEnemy;
    }

    public void setPositionEnemy(Point positionEnemy) {
        PositionEnemy = positionEnemy;
    }
}
