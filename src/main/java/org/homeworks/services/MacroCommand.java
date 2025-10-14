package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Command;

public class MacroCommand implements Command {
  private final Command[] commands;

  public MacroCommand(Command... commands) {
    this.commands = commands;
  }

  @Override
  public void execute() throws CommandException {
    try {
      for (Command command : commands) {
        command.execute();
      }
    } catch (Exception e) {
      if (e instanceof CommandException) {
        throw (CommandException) e;
      }
      throw new CommandException("MacroCommand failed: " + e.getMessage());
    }
  }
}