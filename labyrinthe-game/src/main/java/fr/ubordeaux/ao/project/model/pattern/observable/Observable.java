package fr.ubordeaux.ao.project.model.pattern.observable;

import fr.ubordeaux.ao.project.model.pattern.observable.Observer;

public interface Observable {
    void addObserver(Observer observer);
    void notifyPlayer();
    void notifyEnemy();
    void notifyBomb();
    void notifyExplosion();
    void notifyGameOver();
}
