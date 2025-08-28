package org.homeworks.interfaces;

import org.homeworks.models.Point;

public interface Positionable {
    Point getPosition();
    void setPosition(Point newPosition);
}
