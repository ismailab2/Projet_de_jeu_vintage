package fr.ubordeaux.ao.project.model.logic;

import fr.ubordeaux.ao.project.model.Game;
import fr.ubordeaux.ao.project.model.entity.Enemy;
import fr.ubordeaux.ao.project.model.entity.Player;

//class qui gere qui a gagné et le score du joueur au fil de la partie
public class Rules{
    private final Game game;
    public boolean isWin; //le joueur a t il gagné ?

    private int playerScore; //score du joueur qui sera augmenté en cassant des caisses

    public Rules (Game game){
        this.game = game;
        this.isWin = false;}


    // Fonction de fin de partie, qui vérifie l’état du jeu pour victoire/défaite et appel l'ecran de game over
    //Affiche a gagné car non implementé dans la vue
    public void endGame() {
        Player player = game.getPlayer();
        Enemy enemy = game.getEnemy();

        if (!player.isAlive()) {
            isWin = false;
            System.out.println("Le joueur a perdu !");
        }

        if (enemy != null && !enemy.isAlive()) {
            isWin = true;
            System.out.println("Le joueur a gagné !");
        }
        game.notifyGameOver();
    }

    //ajoute des point au joueur
    //affiche son score, car non implémenté dans vue
    public void addPlayerPoint(int point){
        this.playerScore = this.playerScore + point;
        System.out.println(this.playerScore);
    }

    public int getPlayerPoint(){
        return this.playerScore;
    }
}
