package fr.ubordeaux.ao.project.model;

import fr.ubordeaux.ao.project.model.entity.Bomb;
import fr.ubordeaux.ao.project.model.entity.Enemy;
import fr.ubordeaux.ao.project.model.entity.Explosion;
import fr.ubordeaux.ao.project.model.entity.Player;
import fr.ubordeaux.ao.project.model.enums.CellType;
import fr.ubordeaux.ao.project.model.enums.Direction;
import fr.ubordeaux.ao.project.model.logic.Collision;
import fr.ubordeaux.ao.project.model.logic.Movement;
import fr.ubordeaux.ao.project.model.logic.PlaceBombRandom;
import fr.ubordeaux.ao.project.model.logic.Rules;
import fr.ubordeaux.ao.project.model.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Grid labyrinth;
    private final Player player;
    private Enemy enemy;

    private final Collision collisionManager;
    private final Movement movementManager;
    private Rules rulesManager;

    private final List<Bomb> bombs;
    private final PlaceBombRandom bombPlacer;
    private final List<Explosion> explosions;

    private boolean gameOver;
    private boolean multiplayerMode;
    private boolean playerWon;
    private boolean playerLost;

    // Constructeur par défaut
    public Game() {
        int defaultXsize = 8;
        int defaultYsize = 8;

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
                cell[k][m] = new Cell(CellType.BOX_FIXE, new Point(k, m));
            }
        }

        this.labyrinth = new Grid(cell, defaultXsize, defaultYsize);
        this.player = new Player(new Point(1, 1));
        this.enemy = new Enemy(new Point(2, 2));

        this.collisionManager = new Collision(this);
        this.movementManager = new Movement(this);
        this.rulesManager = new Rules(this, false);

        this.bombs = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.bombPlacer = new PlaceBombRandom(this, bombs);

        this.gameOver = false;
        this.multiplayerMode = false;
        this.playerWon = false;
        this.playerLost = false;
    }

    // Constructeur personnalisé
    public Game(Grid labyrinth, Player player) {
        this.labyrinth = labyrinth;
        this.player = player;

        this.collisionManager = new Collision(this);
        this.movementManager = new Movement(this);
        this.rulesManager = new Rules(this, false);

        this.bombs = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.bombPlacer = new PlaceBombRandom(this, bombs);

        this.gameOver = false;
        this.multiplayerMode = false;
        this.playerWon = false;
        this.playerLost = false;
    }

    // Debug print
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

    // Mouvement du joueur
    public void movePlayer(Direction direction) {
        if (collisionManager.explosionCollision(direction)) {
            player.setAlive(false);
            playerLost = true;
        }
        if (collisionManager.playerCollision(direction)) {
            movementManager.playerMovement(direction);
        }
    }

    public void moveEnemy() {

        Direction randomDir = Direction.getRandomDirection();

        if (collisionManager.explosionCollision(randomDir)) {
            enemy.setAlive(false);
            return;
        }

        if (collisionManager.enemyCollision(randomDir)) {
            movementManager.enemyMovement(randomDir);
        }
    }


    public void placeBomb(int power) {
        bombPlacer.placeBombRandom(power);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bomb> getBombs() {
        return bombs;
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

    public void setGameOver(boolean b) {
        this.gameOver = b;
        if (b) {
            System.out.println("La partie est terminee !");
            if (playerWon) {
                System.out.println("Le joueur a gagne !");
            } else if (playerLost) {
                System.out.println("Le joueur a perdu !");
            }
        }
    }

    public void setPlayerWon(boolean b) {
        this.playerWon = b;
    }

    public void setPlayerLost(boolean b) {
        this.playerLost = b;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isMultiPlayerMode() {
        return multiplayerMode;
    }

    public Rules getRulesManager() {
        return rulesManager;
    }
}
