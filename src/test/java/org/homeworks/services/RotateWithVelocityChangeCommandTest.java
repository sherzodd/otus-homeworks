package org.homeworks.services;

import org.homeworks.interfaces.Rotatable;
import org.homeworks.interfaces.VelocityChangeable;
import org.homeworks.models.Vector;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RotateWithVelocityChangeCommandTest {

  interface RotatableWithVelocity extends Rotatable, VelocityChangeable {}

  @Test
  void testRotateWithVelocityChange_BothOperationsExecuted() throws Exception {
    RotatableWithVelocity object = mock(RotatableWithVelocity.class);
    when(object.getDirection()).thenReturn(0);
    when(object.getAngularVelocity()).thenReturn(45);
    when(object.getDirectionsNumber()).thenReturn(360);
    when(object.getVelocity()).thenReturn(new Vector(10, 0));

    RotateWithVelocityChangeCommand command = new RotateWithVelocityChangeCommand(object);
    command.execute();

    verify(object).setDirection(45);
    verify(object).setVelocity(any(Vector.class));
  }

  @Test
  void testRotateWithVelocityChange_OnlyRotatable_OnlyRotates() throws Exception {
    Rotatable object = mock(Rotatable.class);
    when(object.getDirection()).thenReturn(0);
    when(object.getAngularVelocity()).thenReturn(45);
    when(object.getDirectionsNumber()).thenReturn(360);

    RotateWithVelocityChangeCommand command = new RotateWithVelocityChangeCommand(object);
    command.execute();

    verify(object).setDirection(45);
  }
}