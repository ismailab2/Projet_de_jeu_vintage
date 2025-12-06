package fr.ubordeaux.ao.project.model.pattern.observable;

//fonction de notification, differencié en fonction de ce qui a changé dans le Model
public interface Observable {
    void addObserver(Observer observer);

    void notifyPlayer();
    void notifyEnemy();
    void notifyBomb();
    void notifyExplosion();
    void notifyGameOver();
}
