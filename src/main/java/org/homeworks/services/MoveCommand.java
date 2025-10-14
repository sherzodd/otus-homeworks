package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.Movable;
import org.homeworks.interfaces.Positionable;
import org.homeworks.models.Point;
import org.homeworks.models.Vector;

public class MoveCommand implements Command {
  private final Object object;

  public MoveCommand(Object object) {
    this.object = object;
  }

  @Override
  public void execute() throws CommandException {
    if (!(object instanceof Positionable) || !(object instanceof Movable)) {
      throw new CommandException("Object must implement both Positionable and Movable");
    }

    Positionable positionable = (Positionable) object;
    Movable movable = (Movable) object;

    Point currentPos = positionable.getPosition();
    Vector velocity = movable.getVelocity();

    if (currentPos == null || velocity == null) {
      throw new CommandException("Position or velocity is not available");
    }

    Point newPos = new Point(
            currentPos.getX() + velocity.getDx(),
            currentPos.getY() + velocity.getDy()
    );
    positionable.setPosition(newPos);
  }
}