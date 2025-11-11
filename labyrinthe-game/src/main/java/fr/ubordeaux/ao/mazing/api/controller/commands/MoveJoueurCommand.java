package fr.ubordeaux.ao.mazing.api;

public class MoveCommand implements Command {
    private Joueur joueur;
    private int dx, dy;
    private int prevX, prevY;

    public MoveCommand(Joueur joueur, int dx, int dy) {
        this.joueur = joueur;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        prevX = joueur.getX();
        prevY = joueur.getY();
        joueur.move(dx, dy);  // correction : dx, dy
    }

    @Override
    public void undo() {
        joueur.move(prevX - joueur.getX(), prevY - joueur.getY()); // correction ; ajouté
    }
}
