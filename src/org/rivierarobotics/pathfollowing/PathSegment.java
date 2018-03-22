package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.util.Vector2d;

public class PathSegment {

    private Vector2d startPoint;
    private Vector2d endPoint;
    private Vector2d delta;
    private double length;
    
    public PathSegment(Vector2d start, Vector2d end) {
        startPoint = start;
        endPoint = end;
        delta = endPoint.subtract(startPoint);
        length = start.subtract(end).getMagnitude();
    }
    
    /**
     * 
     * @param dist - distance along segment to travel
     * @return (x,y) location of point that is a given distance along segment
     */
    public Vector2d getPositionByLength(double dist) {
        dist = (dist < 0 ? 0.0 : Math.min(dist,length));
        return startPoint.add(delta.normalize(dist));
    }
    
    /**
     * 
     * @param point - an (x,y) location
     * @return the distance along the path of the closest point to {@code point}. Inverse of {@code getPositionByLength}
     */
    public double getLengthByPosition(Vector2d point) {
        Vector2d closest = getClosestPoint(point);
        return closest.subtract(startPoint).getMagnitude();
    }
    
    /**
     * 
     * @param other - a Vector2d representing another (x,y) location
     * @return the closest point to {@code other} that is on the segment
     */
    public Vector2d getClosestPoint(Vector2d other) {
        Vector2d diff = other.subtract(startPoint);
        Vector2d proj = diff.projectOntoOther(delta);
        double d;
        if(delta.getY() != 0) {
            d = proj.getY()/delta.getY()*getLength();
        }
        else {
            d = proj.getX()/delta.getX()*getLength();
        }
        return getPositionByLength(d);

    }
    
    /**
     * 
     * @param other - a Vector2d representing another (x,y) location
     * @param lookahead - the distance to slide the point along the segment
     * @return the translated location (will not slide beyond endpoints)
     */
    public Vector2d advancePoint(Vector2d other, double lookahead) {
        Vector2d closest = getClosestPoint(other);
        double d1 = closest.subtract(startPoint).getY()/delta.getY()*getLength();
        double d2 = d1 + lookahead;
        return getPositionByLength(d2);
    }
    
    public double getLength() {
        return length;
    }
    
    public Vector2d getBeginning() {
        return startPoint;
    }
    
    public Vector2d getEnd() {
        return endPoint;
    }
}
