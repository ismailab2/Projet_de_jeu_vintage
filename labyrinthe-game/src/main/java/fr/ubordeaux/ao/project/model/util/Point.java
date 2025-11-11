package fr.ubordeaux.ao.project.model.util;

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

    static public int distance(Point p1, Point p2){
        return abs(p1.getX()-p2.getX()) + abs(p1.getY()-p2.getY());
    }

    private static int abs(int x) {
        if(x>0){
            return x;
        }
        else {
            return -x;
        }
    }
}
