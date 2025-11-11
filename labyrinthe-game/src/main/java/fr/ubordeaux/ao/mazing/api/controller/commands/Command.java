package fr.ubordeaux.ao.mazing.api.controller.commands;

public interface Command {
    void execute();
    void undo();
}
