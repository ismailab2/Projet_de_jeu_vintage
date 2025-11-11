package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.util.Point;

//une objet de cette classe represente une case du labyrinth en particulier, son type et sa position
public class Cell {
    CellType cellType;
    Point position;

    public Cell(CellType cellType, Point position){
        this.cellType = cellType;
        this.position = position;
    }

    public CellType getCellType() {
        return cellType;
    }

    public Point getPosition() {
        return position;
    }
}
