package org.homeworks.services;

import org.homeworks.interfaces.Command;

public class LogCommand implements Command {
    private final Command failedCmd;
    private final Exception exception;

    public LogCommand(Command failedCmd, Exception exception) {
        this.failedCmd = failedCmd;
        this.exception = exception;
    }

    @Override
    public void execute() {
        System.out.println("Logging: Command " + failedCmd + " failed with " + exception.getMessage());
    }
}

