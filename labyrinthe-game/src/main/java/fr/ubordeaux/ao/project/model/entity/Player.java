package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.util.Point;

public class Player {
    private Point position;

    public Player(Point position){
        this.position = position;
    }

    public Point getPlayerPosition() {
        return position;
    }

    public void setPlayerPosition(Point newPosition){
        this.position = newPosition;
    }
}
