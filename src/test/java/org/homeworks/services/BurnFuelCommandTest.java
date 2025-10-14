package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Fueled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BurnFuelCommandTest {

  @Test
  void testBurnFuelCommand_ReducesFuel() throws Exception {
    Fueled fueled = mock(Fueled.class);
    when(fueled.getFuelLevel()).thenReturn(100);
    when(fueled.getFuelConsumptionRate()).thenReturn(10);

    BurnFuelCommand command = new BurnFuelCommand(fueled);
    command.execute();

    verify(fueled).setFuelLevel(90);
  }

  @Test
  void testBurnFuelCommand_ReducesToZero() throws Exception {
    Fueled fueled = mock(Fueled.class);
    when(fueled.getFuelLevel()).thenReturn(10);
    when(fueled.getFuelConsumptionRate()).thenReturn(10);

    BurnFuelCommand command = new BurnFuelCommand(fueled);
    command.execute();

    verify(fueled).setFuelLevel(0);
  }

  @Test
  void testBurnFuelCommand_NullObject_ThrowsException() {
    BurnFuelCommand command = new BurnFuelCommand(null);

    assertThrows(CommandException.class, () -> command.execute());
  }
}