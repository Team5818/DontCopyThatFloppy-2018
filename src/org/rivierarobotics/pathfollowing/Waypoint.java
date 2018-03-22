package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.util.Vector2d;

public class Waypoint {

    private Vector2d position;
    private double distanceAlongPath;

    public Waypoint(Vector2d pos, double dist) {
        position = pos;
        distanceAlongPath = dist;
    }

    public Waypoint(Vector2d pos) {
        this(pos, Double.NaN);
    }

    public Vector2d getPosition() {
        return position;
    }

    public double getDistanceAlongPath() {
        return distanceAlongPath;
    }

    public void setDistance(double dist) {
        distanceAlongPath = dist;
    }
}