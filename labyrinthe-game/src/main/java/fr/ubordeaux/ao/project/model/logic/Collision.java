package fr.ubordeaux.ao.project.model.logic;

import fr.ubordeaux.ao.project.model.Cell;
import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.model.util.Point;

//régit les regles lié au collision (le joueur peut il avancer, peut il poser une bombe ici, ...)
public class Collision {
    Game game;

    public Collision(Game game) {
        this.game = game;
    }

    //renvoi true si le joueur peut marcher vers une direction (une cell de type ground)
    public boolean playerWalkDirection(Direction direction) {
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getPlayer().getPlayerPosition());

        return game.getGrid().getCell(newPosition).getCellType() == CellType.GROUND;
    }

    //renvoi true si l'ennemi peut marcher vers une direction (une cell de type ground)
    public boolean enemyWalkDirection(Direction direction) {
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getEnemy().getPositionEnemy());

        return game.getGrid().getCell(newPosition).getCellType() == CellType.GROUND;
    }

    //renvoi true si le player marche vers une case contenant une explosion (en fonction d'une direction)
    public boolean playerExplosionCollision(Direction direction) {
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getPlayer().getPlayerPosition());

        return game.getGrid().getCell(newPosition).getCellType() == CellType.EXPLOSION;
    }

    //renvoi true si l'ennemi marche vers une case contenant une explosion (en fonction d'une direction)
    public boolean ennemyExplosionCollision(Direction direction) {
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getEnemy().getPositionEnemy());

        return game.getGrid().getCell(newPosition).getCellType() == CellType.EXPLOSION;
    }

    // Ce methode sert a vérifier si on peut poser une bombe à sa position
    public Boolean canPlaceBomb(Point point){
        Cell cell = game.getGrid().getCell(point);
        return cell.getCellType() == CellType.GROUND;
    }
}
