package org.homeworks.services;

import org.homeworks.interfaces.Command;

public class RetryCommand implements Command {
    private final Command originalCmd;

    public RetryCommand(Command originalCmd) {
        this.originalCmd = originalCmd;
    }

    @Override
    public void execute() throws Exception {
        originalCmd.execute();
    }
}
