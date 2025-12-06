package fr.ubordeaux.ao.project.model.logic;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.model.util.Point;

//cette class implement les deplacement du joueur voir plus
public class Movement {
    Game game;

    public Movement(Game game) {
        this.game = game;
    }

    //fait avancer le joueur vers une direction dans le laby
    public void playerMovement(Direction direction){
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getPlayer().getPlayerPosition());

        this.game.getPlayer().setPlayerPosition(newPosition);
    }

    //fait avancer l'ennemi vers une direction dans le laby
    public void enemyMovement(Direction direction){
        Point newPosition = Point.sum(Point.directionToPoint(direction), game.getEnemy().getPositionEnemy());

        this.game.getEnemy().setPositionEnemy(newPosition);
    }
}
