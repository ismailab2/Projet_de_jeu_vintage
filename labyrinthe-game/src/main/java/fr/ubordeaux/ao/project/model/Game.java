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

        for(int i = 0; i<defaultXsize; i++){
            for(int j = 0; j<defaultYsize; j++){
                cell[i][j] = new Cell(CellType.GROUND,new Point(i,j));

                //entourer le laby de wall
                if(i==0 || j==0 || i==(defaultXsize-1) || j==(defaultYsize-1)){
                    cell[i][j] = new Cell(CellType.WALL,new Point(i,j));
                }

                //mettre quelques caisse a des position arbitraire
                else if ((i==3 && j==3) || (i==4 && j==2)) {
                    cell[i][j] = new Cell(CellType.BOX,new Point(i,j));
                }

                else {
                    cell[i][j] = new Cell(CellType.GROUND,new Point(i,j));
                }
            }
        }

        this.labyrinth = new Grid(cell, defaultXsize, defaultYsize);
        this.player = new Player(new Point(1,1));
    }

    //constructeure pour une game personalisé
    public Game(Grid labyrinth, Player player){
        this.labyrinth = labyrinth;
        this.player = player;
    }

    //quick written print for model debug
    public void printGame(){
        for(int i=0; i<this.labyrinth.getxSize(); i++){
            for(int j=0; j<this.labyrinth.getySize(); j++){
                if(player.getPlayerPosition().getX() == i && player.getPlayerPosition().getY() == j){
                    System.out.print('P');
                } else{
                    Cell currentCell = this.labyrinth.getCell(new Point(i,j));

                    switch (currentCell.getCellType()){
                        case GROUND -> System.out.print(' ');
                        case WALL -> System.out.print('.');
                        case BOX -> System.out.print('X');
                    }
                }

                if(j == this.labyrinth.getySize()-1){
                    System.out.println();
                }
            }
        }
    }
}
