package org.homeworks.services;

import org.homeworks.interfaces.Rotatable;

public class RotateAction {
    public void rotate(Rotatable rotatable, int angleDelta) {
        if (rotatable == null) {
            throw new IllegalArgumentException("Object must support rotation");
        }
        int newDirection = (rotatable.getDirection() + angleDelta) % 360;
        if (newDirection < 0) newDirection += 360;
        rotatable.setDirection(newDirection);
    }
}

