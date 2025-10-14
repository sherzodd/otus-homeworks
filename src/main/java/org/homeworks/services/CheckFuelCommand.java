
package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.Fueled;

public class CheckFuelCommand implements Command {
  private final Fueled fueled;

  public CheckFuelCommand(Fueled fueled) {
    this.fueled = fueled;
  }

  @Override
  public void execute() throws CommandException {
    if (fueled == null) {
      throw new CommandException("Fueled object cannot be null");
    }

    int fuelLevel = fueled.getFuelLevel();
    int consumptionRate = fueled.getFuelConsumptionRate();

    if (fuelLevel < consumptionRate) {
      throw new CommandException("Not enough fuel: current=" + fuelLevel + ", required=" + consumptionRate);
    }
  }
}