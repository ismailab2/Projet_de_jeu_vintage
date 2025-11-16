package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.util.Point;

//une objet de cette classe represente une case du labyrinth en particulier, son type et sa position
//pas forcement utile, sera peut etre simplifié par grid qui contiendra un tableau d"enum
public class Cell {
    CellType cellType, originalType;
    Point position;

    public Cell(CellType cellType, Point position){
        this.cellType = cellType;
        this.position = position;
        this.originalType = cellType;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public CellType getOriginalType() {
        return originalType;
    }

    public Point getPosition() {
        return position;
    }
}
