package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.Rotatable;

public class RotateCommand implements Command {
  private final Rotatable rotatable;

  public RotateCommand(Rotatable rotatable) {
    this.rotatable = rotatable;
  }

  @Override
  public void execute() throws CommandException {
    if (rotatable == null) {
      throw new CommandException("Rotatable object cannot be null");
    }

    int currentDirection = rotatable.getDirection();
    int angularVelocity = rotatable.getAngularVelocity();
    int directionsNumber = rotatable.getDirectionsNumber();

    int newDirection = (currentDirection + angularVelocity) % directionsNumber;
    if (newDirection < 0) {
      newDirection += directionsNumber;
    }

    rotatable.setDirection(newDirection);
  }
}