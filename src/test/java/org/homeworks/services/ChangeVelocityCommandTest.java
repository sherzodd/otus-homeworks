package org.homeworks.services;

import org.homeworks.interfaces.VelocityChangeable;
import org.homeworks.models.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChangeVelocityCommandTest {

  @Test
  void testChangeVelocity_RotatesVector90Degrees() throws Exception {
    VelocityChangeable object = mock(VelocityChangeable.class);
    when(object.getVelocity()).thenReturn(new Vector(10, 0));

    ChangeVelocityCommand command = new ChangeVelocityCommand(object, 90);
    command.execute();

    verify(object).setVelocity(argThat(v ->
            Math.abs(v.getDx()) <= 1 && Math.abs(v.getDy() - 10) <= 1
    ));
  }

  @Test
  void testChangeVelocity_ObjectNotVelocityChangeable_DoesNothing() throws Exception {
    Object object = new Object();

    ChangeVelocityCommand command = new ChangeVelocityCommand(object, 90);

    assertDoesNotThrow(() -> command.execute());
  }

  @Test
  void testChangeVelocity_NullVelocity_DoesNothing() throws Exception {
    VelocityChangeable object = mock(VelocityChangeable.class);
    when(object.getVelocity()).thenReturn(null);

    ChangeVelocityCommand command = new ChangeVelocityCommand(object, 90);

    assertDoesNotThrow(() -> command.execute());
    verify(object, never()).setVelocity(any());
  }

  @Test
  void testChangeVelocity_Rotate180Degrees() throws Exception {
    VelocityChangeable object = mock(VelocityChangeable.class);
    when(object.getVelocity()).thenReturn(new Vector(10, 5));

    ChangeVelocityCommand command = new ChangeVelocityCommand(object, 180);
    command.execute();

    verify(object).setVelocity(argThat(v ->
            Math.abs(v.getDx() + 10) <= 1 && Math.abs(v.getDy() + 5) <= 1
    ));
  }
}