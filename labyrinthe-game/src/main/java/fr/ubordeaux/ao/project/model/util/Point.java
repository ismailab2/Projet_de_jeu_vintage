package fr.ubordeaux.ao.project.model.util;

import fr.ubordeaux.ao.project.model.enums.Direction;

import java.util.Objects;

import static java.lang.Math.abs;

//classe utilitaire d'un point de base, avec des fonctions de distances
public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    static public Point directionToPoint(Direction direction){
        Point res;
        switch (direction){
            case NORTH -> res = new Point(0,-1);
            case SOUTH -> res = new Point(0,1);
            case WEST -> res = new Point(-1,0);
            case EAST -> res = new Point(1,0);
            default -> throw new IllegalArgumentException("Mauvais enum direction donnée");
        }
        return res;
    }

    //calcul la somme entre deux points
    static public Point sum(Point p1, Point p2){
        return new Point(p1.getX() + p2.getX() , p1.getY() + p2.getY());
    }

    static public int distance(Point p1, Point p2){
        return abs(p1.getX()-p2.getX()) + abs(p1.getY()-p2.getY());
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point point)) return false;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
