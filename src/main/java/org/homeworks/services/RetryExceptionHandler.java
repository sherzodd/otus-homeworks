package org.homeworks.services;

import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.ExceptionHandler;

import java.util.concurrent.BlockingQueue;

public class RetryExceptionHandler implements ExceptionHandler {
    private final BlockingQueue<Command> queue;

    public RetryExceptionHandler(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    @Override
    public Command handle(Command failedCmd, Exception e) {
        Command retryCmd = new RetryCommand(failedCmd);
        queue.add(retryCmd);
        return retryCmd;
    }
}

