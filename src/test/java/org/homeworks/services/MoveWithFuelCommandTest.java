
package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Fueled;
import org.homeworks.interfaces.Movable;
import org.homeworks.interfaces.Positionable;
import org.homeworks.models.Point;
import org.homeworks.models.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveWithFuelCommandTest {

  interface FueledMovable extends Fueled, Movable, Positionable {}

  @Test
  void testMoveWithFuel_Success() throws Exception {
    FueledMovable object = mock(FueledMovable.class);
    when(object.getFuelLevel()).thenReturn(100);
    when(object.getFuelConsumptionRate()).thenReturn(10);
    when(object.getPosition()).thenReturn(new Point(0, 0));
    when(object.getVelocity()).thenReturn(new Vector(5, 5));

    MoveWithFuelCommand command = new MoveWithFuelCommand(object);
    command.execute();

    verify(object).setFuelLevel(90);
    verify(object).setPosition(argThat(p -> p.getX() == 5 && p.getY() == 5));
  }

  @Test
  void testMoveWithFuel_NotEnoughFuel_ThrowsAndDoesNotMove() {
    FueledMovable object = mock(FueledMovable.class);
    when(object.getFuelLevel()).thenReturn(5);
    when(object.getFuelConsumptionRate()).thenReturn(10);

    MoveWithFuelCommand command = new MoveWithFuelCommand(object);

    assertThrows(CommandException.class, () -> command.execute());

    verify(object, never()).setPosition(any(Point.class));
    verify(object, never()).setFuelLevel(anyInt());
  }
}