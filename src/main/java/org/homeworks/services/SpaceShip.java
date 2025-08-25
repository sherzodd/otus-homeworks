package org.homeworks.services;

import org.homeworks.interfaces.Movable;
import org.homeworks.interfaces.Positionable;
import org.homeworks.interfaces.Rotatable;
import org.homeworks.models.Point;
import org.homeworks.models.Vector;

public class SpaceShip implements Positionable, Movable, Rotatable {
    private Point position;
    private final Vector velocity;
    private int direction;

    public SpaceShip(Point position, Vector velocity, int direction) {
        this.position = position;
        this.velocity = velocity;
        this.direction = direction;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point newPosition) {
        this.position = newPosition;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void setDirection(int newDirection) {
        this.direction = newDirection;
    }
}

