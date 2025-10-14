package org.homeworks.services;

import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.Rotatable;

public class RotateWithVelocityChangeCommand extends MacroCommand {
  public RotateWithVelocityChangeCommand(Object object) {
    super(
            new RotateCommand((Rotatable) object),
            new ChangeVelocityCommand(object, ((Rotatable) object).getAngularVelocity())
    );
  }
}