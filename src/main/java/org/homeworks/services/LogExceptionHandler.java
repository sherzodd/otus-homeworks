package org.homeworks.services;

import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.ExceptionHandler;

import java.util.concurrent.BlockingQueue;

public class LogExceptionHandler implements ExceptionHandler {
    private final BlockingQueue<Command> queue;

    public LogExceptionHandler(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    @Override
    public Command handle(Command failedCmd, Exception e) {
        Command logCmd = new LogCommand(failedCmd, e);
        queue.add(logCmd);
        return logCmd;
    }
}

