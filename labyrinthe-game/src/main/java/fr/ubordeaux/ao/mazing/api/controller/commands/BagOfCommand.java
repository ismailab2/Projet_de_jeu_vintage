package fr.ubordeaux.ao.mazing.api.controller.commands;

import java.util.LinkedList;
import java.util.Queue;

public class BagOfCommand {
    private Queue<Command> queue;

    public BagOfCommand() {
        queue = new LinkedList<>();
    }

    public void addCommand(Command command) {
        queue.add(command);
        System.out.println("Commande ajoutée est: " + command.getClass().getSimpleName());
    }

    public void executeAll() {
        System.out.println(" toutes les commandes s'executent ...");
        while (!queue.isEmpty()) {
            Command command = queue.poll();
            command.execute();
        }
    }

    public void clear() {
        queue.clear();
        System.out.println("Youpi Sac de commandes est vidé.");
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
