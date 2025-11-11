package fr.ubordeaux.ao.mazing.api.controller.commands;

import fr.ubordeaux.ao.mazing.api.model.*;
public class PlaceBombCommand implements Command {

    private Labyrinthe labyrinthe;
    private Joueur joueur;
    //private Bomb bomb;

    public PlaceBombCommand(Labyrinthe labyrinthe, Joueur joueur) {
        this.labyrinthe = labyrinthe;
        this.joueur = joueur;
    }

    @Override
    public void execute() {
        //bomb = new Bomb(joueur.getX(), joueur.getY());
        //labyrinthe.setCell(bomb.getX(), bomb.getY(), bomb);
    }

    @Override
    public void undo() {
        //labyrinthe.setCell(bomb.getX(), bomb.getY(), null);
    }


}
