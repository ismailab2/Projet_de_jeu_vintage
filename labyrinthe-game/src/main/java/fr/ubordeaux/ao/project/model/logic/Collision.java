package fr.ubordeaux.ao.project.model.logic;

import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.model.util.Point;

import javax.swing.text.Position;

//réit les régle lié au collision (le joueur peut il avancer, peut il poser une bombe ici)
public class Collision {
    Game game;

    public Collision(Game game) {
        this.game = game;
    }

    //test la collision entre la position du joueur et une direction
    public boolean playerCollision(Direction direction) {
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getPlayer().getPlayerPosition());

        //System.out.print(newPosition.getX());
        //System.out.println(newPosition.getY());

        return game.getGrid().getCell(newPosition).getCellType() == CellType.GROUND;
    }

    public boolean enemyCollision(Direction direction) {
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getPlayer().getPlayerPosition());

        //System.out.print(newPosition.getX());
        //System.out.println(newPosition.getY());

        return game.getGrid().getCell(newPosition).getCellType() == CellType.GROUND;
    }

    //test la collision entre la position du joueur et une direction
    public boolean explosionCollision(Direction direction) {
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getPlayer().getPlayerPosition());

        //System.out.print(newPosition.getX());
        //System.out.println(newPosition.getY());

        return game.getGrid().getCell(newPosition).getCellType() == CellType.EXPLOSION;
    }

    // Ce methode sert a vérifier si le joueur peut poser une bombe à sa position
    public Boolean canPlaceBomb(Point point){
        Cell cell = game.getGrid().getCell(point);
        return cell.getCellType() == CellType.GROUND;
    }

}
