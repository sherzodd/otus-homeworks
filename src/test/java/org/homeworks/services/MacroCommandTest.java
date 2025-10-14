package org.homeworks.services;

import org.homeworks.exceptions.CommandException;
import org.homeworks.interfaces.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MacroCommandTest {

  @Test
  void testMacroCommand_AllCommandsExecuted() throws Exception {
    Command cmd1 = mock(Command.class);
    Command cmd2 = mock(Command.class);
    Command cmd3 = mock(Command.class);

    MacroCommand macro = new MacroCommand(cmd1, cmd2, cmd3);
    macro.execute();

    verify(cmd1).execute();
    verify(cmd2).execute();
    verify(cmd3).execute();
  }

  @Test
  void testMacroCommand_StopsOnException() throws Exception {
    Command cmd1 = mock(Command.class);
    Command cmd2 = mock(Command.class);
    Command cmd3 = mock(Command.class);

    doThrow(new CommandException("Test exception")).when(cmd2).execute();

    MacroCommand macro = new MacroCommand(cmd1, cmd2, cmd3);

    assertThrows(CommandException.class, () -> macro.execute());

    verify(cmd1).execute();
    verify(cmd2).execute();
    verify(cmd3, never()).execute();
  }

  @Test
  void testMacroCommand_EmptyCommands() {
    MacroCommand macro = new MacroCommand();

    assertDoesNotThrow(() -> macro.execute());
  }

  @Test
  void testMacroCommand_WrapsNonCommandException() throws Exception {
    Command cmd = mock(Command.class);
    doThrow(new RuntimeException("Generic error")).when(cmd).execute();

    MacroCommand macro = new MacroCommand(cmd);

    CommandException exception = assertThrows(CommandException.class, () -> macro.execute());
    assertTrue(exception.getMessage().contains("MacroCommand failed"));
  }
}