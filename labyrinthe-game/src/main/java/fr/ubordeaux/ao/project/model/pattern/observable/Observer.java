package fr.ubordeaux.ao.project.model.pattern.observable;

public interface Observer {
    void updatePlayer();
    void updateEnemy();
    void updateBomb();
    void updateExplosion();
    void updateGameOver();
}
