package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.util.Point;

public class Cell {
    CellType cellType;
    Point position;

    public Cell(CellType cellType, Point position){
        this.cellType = cellType;
        this.position = position;
    }
}
