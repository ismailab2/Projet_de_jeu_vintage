package fr.ubordeaux.ao.project.model.logic;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.entity.Bomb;
import fr.ubordeaux.ao.project.model.entity.Enemy;
import fr.ubordeaux.ao.project.model.entity.Explosion;
import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.model.util.Point;

import java.util.EnumSet;
import java.util.List;
import java.util.Iterator;


public class Rules{

    private final Game game;
    public boolean isWin;
    private final boolean multiPlayerMode ;

    private int playerScore;

    public Rules (Game game, Boolean isMultiPlayerMode){
        this.game = game;
        this.isWin = false;
        this.multiPlayerMode = isMultiPlayerMode;
    }


    // Vérifie l’état du jeu pour victoire/défaite
    public void endGame() {
        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        if (!player.isAlive()) {
            System.out.println("Le joueur a perdu !");
            return;
        }

        if (enemy != null && !enemy.isAlive()) {
            isWin = true;
            System.out.println("Le joueur a gagné !");
        }
    }

    public void addPlayerPoint(int point){
        this.playerScore = this.playerScore + point;
    }

    public int getPlayerPoint(){
        return this.playerScore;
    }
}
