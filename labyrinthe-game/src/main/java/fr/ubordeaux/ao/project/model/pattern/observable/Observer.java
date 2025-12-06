package fr.ubordeaux.ao.project.model.pattern.observable;

//fonction d'update, differencié en fonction de ce qui a changé dans le Model
public interface Observer {
    void updatePlayer();
    void updateEnemy();
    void updateBomb();
    void updateExplosion();
    void updateGameOver();
}
