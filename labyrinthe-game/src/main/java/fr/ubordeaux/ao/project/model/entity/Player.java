package fr.ubordeaux.ao.project.model.entity;

import fr.ubordeaux.ao.project.model.util.Point;

//cette class modelise le joueur de la partie, mais ne contient pas ca logique de colisions
// et de deplacement, car isolé dans le package logic
public class Player {
    private Point position;
    private boolean alive;  //est il vivant actuellement ?

    private int numberOfBombLeft; //the number of bomb the player can use at the same time

    public Player(Point position) {
        this.position = position;
        this.alive = true;

        this.numberOfBombLeft = 2;
    }

    public Point getPlayerPosition() {
        return position;
    }

    public void setPlayerPosition(Point newPosition) {
        this.position = newPosition;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    //did the player have a bomb avaible, and use it if so
    public boolean placeBomb(){
        if(this.numberOfBombLeft>0){
            this.numberOfBombLeft-=1;
            return true;
        }
        return false;
    }

    //return a bomb in the counter
    public void getBackBomb(){
        numberOfBombLeft+=1;
    }
}
