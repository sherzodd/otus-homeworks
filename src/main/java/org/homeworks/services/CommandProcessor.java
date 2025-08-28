package org.homeworks.services;

import org.homeworks.interfaces.Command;
import org.homeworks.interfaces.ExceptionHandler;

import java.util.concurrent.BlockingQueue;

public class CommandProcessor {
    private final BlockingQueue<Command> queue;
    private final ExceptionHandler handler;

    public CommandProcessor(BlockingQueue<Command> queue, ExceptionHandler handler) {
        this.queue = queue;
        this.handler = handler;
    }

    public void start() {
        boolean stop = false;
        while (!stop) {
            try {
                Command cmd = queue.take();
                cmd.execute();
            } catch (Exception e) {
                // Обрабатываем только базовое исключение
                handler.handle(null, e);
            }
        }
    }
}
