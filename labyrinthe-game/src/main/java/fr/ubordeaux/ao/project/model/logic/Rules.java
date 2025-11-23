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

    public Rules (Game game, Boolean isMultiPlayerMode){
        this.game = game;
        this.isWin = false;
        this.multiPlayerMode = isMultiPlayerMode;
    }

    /*
     * Ce methode sert a vérifier si un joueur est touché par une explosion
     */

    public Boolean checkExplosionPalyer(Player player){

        Point  playerPos = player.getPlayerPosition();
        Cell cell = game.getGrid().getCell(playerPos);
        if(cell.getCellType() == CellType.EXPLOSION){
            player.setAlive(false);
            return true;
        }

        return false;
    }

    private boolean checkExplosionEnemy(Enemy enemy) {
        if (!enemy.isAlive()) return false;

        Point pos = enemy.getPositionEnemy();
        Cell cell = game.getGrid().getCell(pos);
        if (cell.getCellType() == CellType.EXPLOSION) {
            enemy.setAlive(false);
            return true;
        }
        return false;
    }


     // Ce methode sert a vérifier si le joueur peut poser une bombe à sa position

    public Boolean canPlaceBomb(Player player){
        Point  Pos = player.getPlayerPosition();
        Cell cell = game.getGrid().getCell(Pos);
        return cell.getCellType() == CellType.GROUND;
    }

    // Vérifie l’état du jeu pour victoire/défaite

    public void updateGameplay() {
        //  gestion des explosions et destruction des murs
        handleExplosions();

        // vérification si l'ennemi est touché par une explosion
        Enemy enemy = game.getEnemy();
        if (enemy != null) {
            checkExplosionEnemy(enemy);
        }

        // vérification si le joueur est touché par une explosion
        checkExplosionPalyer(game.getPlayer());

        // mise à jour de l’état du jeu
        updateGameStatus();
    }

    private void updateGameStatus() {
        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        if (!player.isAlive()) {
            game.setPlayerLost(true);
            game.setGameOver(true);
            System.out.println("Le joueur a perdu !");
            return;
        }

        if (enemy != null && !enemy.isAlive()) {
            isWin = true;
            game.setPlayerWon(true);
            game.setGameOver(true);
            System.out.println("Le joueur a gagné !");
        }
    }

    private void handleExplosions() {
        List<Bomb> bombs = game.getBombs();
        Iterator<Bomb> iterator = bombs.iterator();

        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();

            // On ne traite que les bombes qui ont explosé
            if (bomb.isExploded()) {
                Explosion explosion = bomb.getExplosion();
                if (explosion != null) {
                    Point center = explosion.getPosition();
                    Cell centerCell = game.getGrid().getCell(center);
                    if (centerCell.getCellType() == CellType.BOX) {
                        centerCell.setCellType(CellType.GROUND);
                        game.getPlayer().setScore(game.getPlayer().getScore() + 10);
                    }

                    // propagation dans les 4 directions
                    for (Direction dir : EnumSet.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
                        Point dirVector = Point.directionToPoint(dir);
                        for (int i = 1; i <= explosion.getPower(); i++) {
                            Point next = new Point(center.getX() + dirVector.getX() * i,
                                    center.getY() + dirVector.getY() * i);

                            if (!game.getGrid().validPosition(next)) break;

                            Cell cell = game.getGrid().getCell(next);
                            CellType type = cell.getCellType();

                            if (type == CellType.WALL || type == CellType.BOX_FIXE) break;

                            if (type == CellType.BOX) {
                                cell.setCellType(CellType.GROUND);
                                game.getPlayer().setScore(game.getPlayer().getScore() + 10);
                                break;
                            }

                            if (type == CellType.GROUND) {
                                cell.setCellType(CellType.EXPLOSION);
                            }
                        }
                    }
                }

                iterator.remove();
            }
        }
    }


    public boolean isWin() {
        return isWin;
    }
}
