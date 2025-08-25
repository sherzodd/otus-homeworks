package org.homeworks.services;

import org.homeworks.interfaces.Movable;
import org.homeworks.interfaces.Positionable;
import org.homeworks.models.Point;
import org.homeworks.models.Vector;

public class MoveAction {
    public void move(Positionable positionable, Movable movable) {
        if (positionable == null || movable == null) {
            throw new IllegalArgumentException("Object must support both Positionable and Movable");
        }

        Point pos = positionable.getPosition();
        Vector vel = movable.getVelocity();

        if (pos == null || vel == null) {
            throw new IllegalStateException("Position or velocity is not available");
        }

        Point newPos = new Point(pos.getX() + vel.getDx(), pos.getY() + vel.getDy());
        positionable.setPosition(newPos);
    }
}

