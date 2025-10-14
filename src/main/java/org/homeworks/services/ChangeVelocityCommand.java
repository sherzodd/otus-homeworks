package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.Rotatable;
import org.homeworks.interfaces.VelocityChangeable;
import org.homeworks.models.Vector;

public class ChangeVelocityCommand implements Command {
  private final Object object;
  private final int angleDelta;

  public ChangeVelocityCommand(Object object, int angleDelta) {
    this.object = object;
    this.angleDelta = angleDelta;
  }

  @Override
  public void execute() throws CommandException {
    // Проверяем, движется ли объект (имеет ли он интерфейс VelocityChangeable)
    if (!(object instanceof VelocityChangeable)) {
      // Объект не движется, ничего не делаем
      return;
    }

    VelocityChangeable velocityChangeable = (VelocityChangeable) object;
    Vector currentVelocity = velocityChangeable.getVelocity();

    if (currentVelocity == null) {
      // Нет вектора скорости, ничего не меняем
      return;
    }

    // Поворачиваем вектор скорости на угол
    double angleRad = Math.toRadians(angleDelta);
    double cos = Math.cos(angleRad);
    double sin = Math.sin(angleRad);

    int newDx = (int) Math.round(currentVelocity.getDx() * cos - currentVelocity.getDy() * sin);
    int newDy = (int) Math.round(currentVelocity.getDx() * sin + currentVelocity.getDy() * cos);

    velocityChangeable.setVelocity(new Vector(newDx, newDy));
  }
}