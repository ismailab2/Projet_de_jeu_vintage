package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.util.Point;

public class Game {
    Grid labyrinth;
    Player player;

    //constructeur utilsé pour lancer la game par default
    public Game(){
        int defaultXsize = 8;
        int defaultYsize = 8;

        Cell[][] cell = new Cell[defaultXsize][defaultYsize];

        for(int i = 0;i<defaultXsize;i++){
            for(int j = 0;j<defaultYsize;j++){
                cell[i][j].position = new Point(i,j);

                //entourer le laby de wall
                if(i==0 || j==0 || i==(defaultXsize-1) || j==(defaultYsize-1)){
                    cell[i][j].cellType = CellType.WALL;
                }

                //mettre quelques caisse a des position arbitraire
                else if ((i==3 && j==3) || (i==4 && j==2)) {
                    cell[i][j].cellType = CellType.BOX;
                }

                else {
                    cell[i][j].cellType = CellType.GROUND;
                }
            }
        }

        this.labyrinth = new Grid(cell, defaultXsize, defaultYsize);
        this.player = new Player(new Point(1,1));
    }

}
