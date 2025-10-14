package org.homeworks.services;

import org.homeworks.interfaces.Command;

public class MoveWithFuelCommand extends MacroCommand {
  public MoveWithFuelCommand(Object object) {
    super(
            new CheckFuelCommand((org.homeworks.interfaces.Fueled) object),
            new MoveCommand(object),
            new BurnFuelCommand((org.homeworks.interfaces.Fueled) object)
    );
  }
}