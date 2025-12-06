package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.entity.Bomb;
import fr.ubordeaux.ao.project.model.entity.Enemy;
import fr.ubordeaux.ao.project.model.entity.Explosion;
import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.model.logic.Collision;
import fr.ubordeaux.ao.project.model.logic.Movement;
import fr.ubordeaux.ao.project.model.logic.Rules;
import fr.ubordeaux.ao.project.model.pattern.observable.Observable;
import fr.ubordeaux.ao.project.model.pattern.observable.Observer;
import fr.ubordeaux.ao.project.model.util.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game implements Observable{
    private final Grid labyrinth;
    private final Player player;
    private Enemy enemy;

    //logique de notre jeu (interaction entre ses elements)
    private final Collision collisionManager;
    private final Movement movementManager;
    private Rules rulesManager;

    //tableaux representant les Bomb/Explosion actuellements presentes dans la game
    private final List<Bomb> bombs;
    private final List<Explosion> explosions;

    //observer
    public LinkedList<Observer> observers = new LinkedList<Observer>();

    //Constructeur qui creer une game par default
    public Game() {
        int defaultXsize = 9;
        int defaultYsize = 9;

        Cell[][] cell = new Cell[defaultXsize][defaultYsize];

        for (int i = 0; i < defaultXsize; i++) {
            for (int j = 0; j < defaultYsize; j++) {
                cell[i][j] = new Cell(CellType.GROUND, new Point(i, j));

                // Entourer le labyrinthe de murs
                if (i == 0 || j == 0 || i == defaultXsize - 1 || j == defaultYsize - 1) {
                    cell[i][j] = new Cell(CellType.WALL, new Point(i, j));
                }
            }
        }

        // Caisses fixes non destructibles
        for (int k = 2; k < defaultXsize; k += 2) {
            for (int m = 2; m < defaultYsize; m += 2) {
                cell[k][m] = new Cell(CellType.WALL, new Point(k, m));
            }
        }

        // Caisses fixes  destructibles
        for (int k = 3; k < defaultXsize-2; k += 2) {
            for (int m = 1; m < defaultYsize; m += 2) {
                cell[k][m] = new Cell(CellType.BOX, new Point(k, m));
            }
        }

        this.labyrinth = new Grid(cell, defaultXsize, defaultYsize);
        this.player = new Player(new Point(1, 1));
        this.enemy = new Enemy(new Point(7, 7));

        this.collisionManager = new Collision(this);
        this.movementManager = new Movement(this);
        this.rulesManager = new Rules(this);

        this.bombs = new ArrayList<>();
        this.explosions = new ArrayList<>();

        this.notifyExplosion();
    }

    //constructeur non utilisé, qui premet de creer une game pour un player/laby donné
    public Game(Grid labyrinth, Player player) {
        this.labyrinth = labyrinth;
        this.player = player;

        this.collisionManager = new Collision(this);
        this.movementManager = new Movement(this);
        this.rulesManager = new Rules(this);

        this.bombs = new ArrayList<>();
        this.explosions = new ArrayList<>();
    }

    //fonction de debug qui print la game (plus a jour)
    public void printGame() {
        for (int j = 0; j < labyrinth.getySize(); j++) {
            for (int i = 0; i < labyrinth.getxSize(); i++) {
                if (player.getPlayerPosition().getX() == i && player.getPlayerPosition().getY() == j) {
                    System.out.print('P');
                } else {
                    Cell currentCell = labyrinth.getCell(new Point(i, j));
                    switch (currentCell.getCellType()) {
                        case GROUND -> System.out.print(' ');
                        case WALL -> System.out.print('.');
                        case BOX -> System.out.print('X');
                        case BOX_FIXE -> System.out.print('O');
                        case BOMB -> System.out.print('B');
                    }
                }
            }
            System.out.println();
        }
    }

    //kill the player
    public void killPlayer(){
        player.setAlive(false);
        rulesManager.endGame();
    }

    //kill the ennemy
    public void killEnnemy(){
        enemy.setAlive(false);
        rulesManager.endGame();
    }

    //Deplace le joueur vers une direction si possible
    public void movePlayer(Direction direction) {
        if(!player.isAlive()){
            return;
        }
        if (collisionManager.playerExplosionCollision(direction)) {
            killPlayer();
            return;
        }
        if (collisionManager.playerWalkDirection(direction)) {
            movementManager.playerMovement(direction);
        }
        this.notifyPlayer();
    }

    //Deplace l'ennemi vers une direction si possible
    public void moveEnemy() {
        if(!player.isAlive()){
            return;
        }

        Direction randomDir = Direction.getRandomDirection();

        if (collisionManager.ennemyExplosionCollision(randomDir)) {
            killEnnemy();
            return;
        }

        if (collisionManager.enemyWalkDirection(randomDir)) {
            movementManager.enemyMovement(randomDir);
        }
        this.notifyEnemy();
    }

    //apellé par le controller, pose une bomb a la pos du joueur si possible
    public void placeBomb(int power) {
        if(this.player.placeBomb()){
            bombs.add(new Bomb(this, player.getPlayerPosition(),1));
            this.notifyBomb();
        }
    }

    //getteurs et setteurs
    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Grid getGrid() {
        return labyrinth;
    }

    public void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }

    public void removeBombe(Bomb bomb) {
        bombs.remove(bomb);
    }

    public void removeExplosion(Explosion explosion) {
        explosions.remove(explosion);
    }

    public Rules getRulesManager() {
        return rulesManager;
    }

    public Collision getCollision(){
        return this.collisionManager;
    }


    //implementation de observer
    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyPlayer() {
        for (Observer observer : this.observers) {
            observer.updatePlayer();
        }
    }

    @Override
    public void notifyEnemy() {
        for (Observer observer : this.observers) {
            observer.updateEnemy();
        }
    }

    @Override
    public void notifyBomb() {
        for (Observer observer : this.observers) {
            observer.updateBomb();
        }
    }

    @Override
    public void notifyExplosion() {
        for (Observer observer : this.observers) {
            observer.updateExplosion();
        }
    }

    @Override
    public void notifyGameOver() {
        for (Observer observer : this.observers) {
            observer.updateGameOver();
        }
    }
}
