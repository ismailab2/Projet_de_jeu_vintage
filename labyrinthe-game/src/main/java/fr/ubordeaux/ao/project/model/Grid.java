package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.util.Point;

//un objet de cette classe represente le labyrinth, composé d'une matrice de cell (case) et de sa taille
public class Grid {
    private Cell[][] labyrinth;

    private int xSize;
    private int ySize;

    public Grid(Cell[][] labyrinth, int xSize, int ySize){
        this.labyrinth = labyrinth;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    //recupere la cell de la position donnée en parametre (la position doit etre valide)
    public Cell getCell(Point position){
        if(position.getX() >= 0 && position.getX() < this.xSize
        && position.getY() >= 0 && position.getY() < this.ySize){
            return this.labyrinth[position.getX()][position.getY()];
        }
        else {
            throw new IllegalArgumentException("La cell n'existe pas pour la position donnée");
        }
    }
}
