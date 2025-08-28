package org.homeworks.interfaces;

public interface ExceptionHandler {
    Command handle(Command failedCmd, Exception e);
}