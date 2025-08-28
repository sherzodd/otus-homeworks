package org.homeworks.services;

import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.ExceptionHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class RetryTwiceThenLogHandler implements ExceptionHandler {
    private final BlockingQueue<Command> queue;
    private final ConcurrentHashMap<Command, Integer> attempts = new ConcurrentHashMap<>();

    public RetryTwiceThenLogHandler(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    @Override
    public Command handle(Command failedCmd, Exception e) {
        int count = attempts.getOrDefault(failedCmd, 0);
        attempts.put(failedCmd, count + 1);

        if (count < 2) {
            Command retry = new RetryCommand(failedCmd);
            queue.add(retry);
            return retry;
        } else {
            Command log = new LogCommand(failedCmd, e);
            queue.add(log);
            return log;
        }
    }
}

