package fr.ubordeaux.ao.project.controller.pattern.commands;

import fr.ubordeaux.ao.mazing.api.model.Joueur;

public class MoveJoueurCommand implements Command {
    private Joueur joueur;
    private int dx, dy;

    private int precX, precY;

    public MoveJoueurCommand(Joueur joueur, int dx, int dy) {
        this.joueur = joueur;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        precX = joueur.getX();
        precY = joueur.getY();
        joueur.move(dx, dy);
    }

    @Override
    public void undo() {
        joueur.move(precX - joueur.getX(), precY - joueur.getY());
    }
}
