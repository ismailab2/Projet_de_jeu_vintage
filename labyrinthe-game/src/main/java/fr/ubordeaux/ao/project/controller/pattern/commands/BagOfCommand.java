package fr.ubordeaux.ao.project.controller.pattern.commands;

import java.util.LinkedList;
import java.util.Queue;

public class BagOfCommand {

    // instance unique (singleton)
    private static BagOfCommand instance;

    //  file des commandes
    private final Queue<Command> queue;

    //  Constructeur privé pour empêcher de faire new BagOfCommand()
    private BagOfCommand() {
        queue = new LinkedList<>();
    }

    // methode d’accès global à l’instance
    public static synchronized BagOfCommand getInstance() {
        if (instance == null) {
            instance = new BagOfCommand();
        }
        return instance;
    }


    public void addCommand(Command command) {
        queue.add(command);
        //System.out.println("Commande ajoutée : " + command.getClass().getSimpleName());
    }

    public void executeAll() {
        //System.out.println("Exécution de toutes les commandes...");
        while (!queue.isEmpty()) {
            Command command = queue.poll();
            command.execute();
        }
    }
}
