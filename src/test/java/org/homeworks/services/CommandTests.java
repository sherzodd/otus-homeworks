package org.homeworks.services;

import org.homeworks.interfaces.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandTests {

    // --- 1. Тест LogCommand ---
    @Test
    void testLogCommandWritesMessage() {
        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("Boom!");

        LogCommand logCmd = new LogCommand(failedCmd, ex);

        assertDoesNotThrow(logCmd::execute);
    }

    // --- 2. Тест LogExceptionHandler ---
    @Test
    void testLogExceptionHandlerAddsLogCommandToQueue() throws Exception {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        LogExceptionHandler handler = new LogExceptionHandler(queue);

        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("Error");

        Command result = handler.handle(failedCmd, ex);

        assertInstanceOf(LogCommand.class, result);
        assertEquals(1, queue.size());
        assertInstanceOf(LogCommand.class, queue.peek());
    }

    // --- 3. Тест RetryCommand ---
    @Test
    void testRetryCommandExecutesOriginalCommand() throws Exception {
        Command original = mock(Command.class);
        RetryCommand retryCmd = new RetryCommand(original);

        retryCmd.execute();

        verify(original, times(1)).execute();
    }

    // --- 4. Тест RetryExceptionHandler ---
    @Test
    void testRetryExceptionHandlerAddsRetryCommandToQueue() throws Exception {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        RetryExceptionHandler handler = new RetryExceptionHandler(queue);

        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("RetryMe");

        Command result = handler.handle(failedCmd, ex);

        assertInstanceOf(RetryCommand.class, result);
        assertEquals(1, queue.size());
        assertInstanceOf(RetryCommand.class, queue.peek());
    }

    // --- 5. Тест RetryOnceThenLogHandler ---
    @Test
    void testRetryOnceThenLogHandler_FirstFailureAddsRetryCommand() {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        RetryOnceThenLogHandler handler = new RetryOnceThenLogHandler(queue);

        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("FailOnce");

        Command result = handler.handle(failedCmd, ex);

        assertInstanceOf(RetryCommand.class, result);
        assertEquals(1, queue.size());
        assertInstanceOf(RetryCommand.class, queue.peek());
    }

    @Test
    void testRetryOnceThenLogHandler_SecondFailureAddsLogCommand() {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        RetryOnceThenLogHandler handler = new RetryOnceThenLogHandler(queue);

        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("FailTwice");

        // 1st failure -> RetryCommand at head
        handler.handle(failedCmd, ex);

        // 2nd failure -> LogCommand appended at tail (result returns the last enqueued)
        Command result = handler.handle(failedCmd, ex);
        assertInstanceOf(LogCommand.class, result);

        // Verify queue order: [RetryCommand, LogCommand]
        assertEquals(2, queue.size());
        Command first = queue.poll();
        Command second = queue.poll();
        assertInstanceOf(RetryCommand.class, first);
        assertInstanceOf(LogCommand.class, second);
    }


    // --- 6. Тест RetryTwiceThenLogHandler ---
    @Test
    void testRetryTwiceThenLogHandler_FirstFailureAddsRetryCommand() {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        RetryTwiceThenLogHandler handler = new RetryTwiceThenLogHandler(queue);

        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("FailOnce");

        Command result = handler.handle(failedCmd, ex);

        assertInstanceOf(RetryCommand.class, result);
        assertEquals(1, queue.size());
        assertInstanceOf(RetryCommand.class, queue.peek());
    }

    @Test
    void testRetryTwiceThenLogHandler_SecondFailureAddsRetryCommandAgain() {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        RetryTwiceThenLogHandler handler = new RetryTwiceThenLogHandler(queue);

        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("FailTwice");

        handler.handle(failedCmd, ex);                // 1st -> Retry
        Command result = handler.handle(failedCmd, ex); // 2nd -> Retry again

        assertInstanceOf(RetryCommand.class, result);
        assertEquals(2, queue.size());

        // Order: [Retry, Retry]
        Command first = queue.poll();
        Command second = queue.poll();
        assertInstanceOf(RetryCommand.class, first);
        assertInstanceOf(RetryCommand.class, second);
    }

    @Test
    void testRetryTwiceThenLogHandler_ThirdFailureAddsLogCommand() {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
        RetryTwiceThenLogHandler handler = new RetryTwiceThenLogHandler(queue);

        Command failedCmd = mock(Command.class);
        Exception ex = new RuntimeException("FailThrice");

        handler.handle(failedCmd, ex);                 // 1st -> Retry
        handler.handle(failedCmd, ex);                 // 2nd -> Retry
        Command result = handler.handle(failedCmd, ex);  // 3rd -> Log

        assertInstanceOf(LogCommand.class, result);
        assertEquals(3, queue.size());

        // Order: [Retry, Retry, Log]
        Command first = queue.poll();
        Command second = queue.poll();
        Command third = queue.poll();
        assertInstanceOf(RetryCommand.class, first);
        assertInstanceOf(RetryCommand.class, second);
        assertInstanceOf(LogCommand.class, third);
    }
}
