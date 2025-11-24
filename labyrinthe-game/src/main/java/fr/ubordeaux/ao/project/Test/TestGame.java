package fr.ubordeaux.ao.project.Test;

import fr.ubordeaux.ao.project.model.*;
import fr.ubordeaux.ao.project.model.entity.*;
import fr.ubordeaux.ao.project.model.enums.Direction;

/*
public class TestGame {
     {

        // créer le jeu
        Game game = new Game();
        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        System.out.println("=== etat initial ===");
        game.printGame();
        System.out.println("Score joueur : " + player.getScore());
        System.out.println("Joueur vivant ? " + player.isAlive());
        System.out.println("Ennemi vivant ? " + enemy.isAlive());

        // déplacer le joueur vers l'Est
        game.movePlayer(Direction.EAST);
        System.out.println("\n=== Après déplacement EAST ===");
        game.printGame();
        System.out.println("Position joueur : " + player.getPlayerPosition());

        // poser une bombe
        System.out.println("\n=== Pose d'une bombe ===");
        game.placeBomb(2);
        game.printGame();

        // forcer explosion immédiate pour test
        if (!game.getBombs().isEmpty()) {
            Bomb bomb = game.getBombs().get(0);
            bomb.explode() ;
        }

        // mettre à jour le gameplay
        System.out.println("\n=== Après explosion et mise à jour ===");
        game.printGame();
        System.out.println("Score joueur : " + player.getScore());
        System.out.println("Joueur vivant ? " + player.isAlive());
        System.out.println("Ennemi vivant ? " + enemy.isAlive());

        // tester victoire/défaite
        if (game.isGameOver()) {
            if (game.getRulesManager().isWin()) {
                System.out.println("Test victoire : Le joueur a gagné !");
            } else {
                System.out.println("Test défaite : Le joueur a perdu !");
            }
        } else {
            System.out.println("La partie continue...");
        }
    }
}
*/