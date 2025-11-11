package fr.ubordeaux.ao.project.model;

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
}
