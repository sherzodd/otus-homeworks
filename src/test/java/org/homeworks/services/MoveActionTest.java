package org.homeworks.services;

import org.homeworks.interfaces.Movable;
import org.homeworks.interfaces.Positionable;
import org.homeworks.models.Point;
import org.homeworks.models.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveActionTest {

    private final MoveAction move = new MoveAction();

    @Test
    void move_changes_position_by_velocity_vector() {
        TestBody obj = new TestBody(new Point(12, 5), new Vector(-7, 3));
        move.move(obj, obj);
        assertEquals(5, obj.getPosition().getX());
        assertEquals(8, obj.getPosition().getY());
    }

    @Test
    void move_fails_when_position_unreadable() {
        PositionUnreadable obj = new PositionUnreadable();
        assertThrows(IllegalStateException.class, () -> move.move(obj, obj));
    }

    @Test
    void move_fails_when_velocity_unreadable() {
        VelocityUnreadable obj = new VelocityUnreadable();
        assertThrows(IllegalStateException.class, () -> move.move(obj, obj));
    }

    @Test
    void move_fails_when_position_unwritable() {
        PositionUnwritable obj = new PositionUnwritable(new Point(0, 0), new Vector(1, 1));
        assertThrows(RuntimeException.class, () -> move.move(obj, obj));
    }

    private static class TestBody implements Positionable, Movable {
        private Point position;
        private final Vector velocity;

        TestBody(Point position, Vector velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        @Override public Point getPosition() { return position; }
        @Override public void setPosition(Point newPosition) { this.position = newPosition; }
        @Override public Vector getVelocity() { return velocity; }
    }

    private static class PositionUnreadable implements Positionable, Movable {
        @Override public Point getPosition() { return null; }
        @Override public void setPosition(Point newPosition) {}
        @Override public Vector getVelocity() { return new Vector(1, 1); }
    }

    private static class VelocityUnreadable implements Positionable, Movable {
        @Override public Point getPosition() { return new Point(10, 10); }
        @Override public void setPosition(Point newPosition) {}
        @Override public Vector getVelocity() { return null; }
    }

    private static class PositionUnwritable implements Positionable, Movable {
        private final Point position;
        private final Vector velocity;

        PositionUnwritable(Point position, Vector velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        @Override public Point getPosition() { return position; }

        @Override
        public void setPosition(Point newPosition) {
            throw new RuntimeException("Position is immutable");
        }

        @Override public Vector getVelocity() { return velocity; }
    }
}
