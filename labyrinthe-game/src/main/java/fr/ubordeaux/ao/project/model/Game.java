package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.model.logic.Collision;
import fr.ubordeaux.ao.project.model.logic.Movement;
import fr.ubordeaux.ao.project.model.logic.Rules;
import fr.ubordeaux.ao.project.model.util.Point;

//un objet de cette classe represente la logique de la partie en cours, avec son joueur et son labytinth pour le moment
public class Game {
    private Grid labyrinth;
    private Player player;

    private Collision collisionManager;
    private Movement movementManager;
    private Rules rulesManager;

    //constructeur utilsé pour lancer la game par default
    //possibilité de changer sa taille en modifiant defaultXsize et defaultYsize
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

        this.collisionManager = new Collision(this);
        this.movementManager = new Movement(this);
        this.rulesManager = new Rules(this);
    }

    //constructeure pour une game personalisé
    public Game(Grid labyrinth, Player player){
        this.labyrinth = labyrinth;
        this.player = player;

        this.collisionManager = new Collision(this);
        this.movementManager = new Movement(this);
        this.rulesManager = new Rules(this);
    }

    //quick writen print for model debug
    public void printGame(){
        for(int j=0; j<this.labyrinth.getySize(); j++){
            for(int i=0; i<this.labyrinth.getxSize(); i++){
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
            }
            System.out.println();
        }
    }

    //bouge le player dans la grid vers une direction donnée, ou non si elle est impossible (vers un mur par exemple)
    public void movePlayer(Direction direction){
        if(collisionManager.playerCollision(direction)){
            movementManager.playerMovement(direction);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Grid getGrid(){
        return this.labyrinth;
    }
}
