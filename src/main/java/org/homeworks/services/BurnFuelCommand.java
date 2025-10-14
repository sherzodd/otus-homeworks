package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.Fueled;

public class BurnFuelCommand implements Command {
  private final Fueled fueled;

  public BurnFuelCommand(Fueled fueled) {
    this.fueled = fueled;
  }

  @Override
  public void execute() throws CommandException {
    if (fueled == null) {
      throw new CommandException("Fueled object cannot be null");
    }

    int currentFuel = fueled.getFuelLevel();
    int consumptionRate = fueled.getFuelConsumptionRate();

    fueled.setFuelLevel(currentFuel - consumptionRate);
  }
}