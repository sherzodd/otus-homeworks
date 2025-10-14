package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Fueled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckFuelCommandTest {

  @Test
  void testCheckFuelCommand_EnoughFuel_Success() {
    Fueled fueled = mock(Fueled.class);
    when(fueled.getFuelLevel()).thenReturn(100);
    when(fueled.getFuelConsumptionRate()).thenReturn(10);

    CheckFuelCommand command = new CheckFuelCommand(fueled);

    assertDoesNotThrow(() -> command.execute());
  }

  @Test
  void testCheckFuelCommand_NotEnoughFuel_ThrowsException() {
    Fueled fueled = mock(Fueled.class);
    when(fueled.getFuelLevel()).thenReturn(5);
    when(fueled.getFuelConsumptionRate()).thenReturn(10);

    CheckFuelCommand command = new CheckFuelCommand(fueled);

    CommandException exception = assertThrows(CommandException.class, () -> command.execute());
    assertTrue(exception.getMessage().contains("Not enough fuel"));
  }

  @Test
  void testCheckFuelCommand_ExactlyEnoughFuel_Success() {
    Fueled fueled = mock(Fueled.class);
    when(fueled.getFuelLevel()).thenReturn(10);
    when(fueled.getFuelConsumptionRate()).thenReturn(10);

    CheckFuelCommand command = new CheckFuelCommand(fueled);

    assertDoesNotThrow(() -> command.execute());
  }

  @Test
  void testCheckFuelCommand_NullObject_ThrowsException() {
    CheckFuelCommand command = new CheckFuelCommand(null);

    assertThrows(CommandException.class, () -> command.execute());
  }
}