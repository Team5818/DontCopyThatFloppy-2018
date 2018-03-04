package org.rivierarobotics.pathfollowing;

import java.util.ArrayList;

import org.rivierarobotics.util.RigidTransformation2d;
import org.rivierarobotics.util.Vector2d;

public class Path {

    private ArrayList<PathSegment> segments;
    private Vector2d startPoint;
    private Vector2d endPoint;
    private int numSegs;
    private double pathLength;

    public Path(ArrayList<PathSegment> segs) {
        segments = segs;
        numSegs = segs.size();
        startPoint = segments.get(0).getBeginning();
        endPoint = segments.get(numSegs-1).getEnd();
        calculatePathLength();
    }

    private void calculatePathLength() {
        double totalLength = 0;
        for (PathSegment seg : segments) {
            totalLength += seg.getLength();
        }
        pathLength = totalLength;
    }

    public ArrayList<PathSegment> getSegs(){
        return segments;
    }
    
    /**
     * 
     * @param distance
     *            to interpolate along the path
     * @return (x,y) point that is @pos units along the path
     */
    public Vector2d getPositionByLength(double distAlongPath) {
        double totalDistance = 0;
        for (int i = 0; i < numSegs; i++) {
            if (distAlongPath <= totalDistance + segments.get(i).getLength()) {
                return segments.get(i).getPositionByLength(distAlongPath - totalDistance);
            }
            totalDistance += segments.get(i).getLength();
        }
        return (distAlongPath <= 0 ? startPoint : endPoint);
    }
    
    /**
     * 
     * @param point - an (x,y) location
     * @return the distance along the path of the closest point to {@code point}. Inverse of {@code getPositionByLength}
     */
    public double getLengthByPosition(Vector2d point) {
        Vector2d closestPoint = getClosestPointOnPath(point);
        PathSegment closestSeg = getClosestSegment(point);
        double totalLength = 0;
        for (int i = 0; i < segments.indexOf(closestSeg); i++) {
            totalLength += segments.get(i).getLength();
        }
        totalLength += closestSeg.getLengthByPosition(closestPoint);
        return totalLength;
    }

    /**
     * 
     * @param otherPos
     *            - an (x,y) position, probably not on the path
     * @return the closest point to {@code otherPos} that is on the path
     */
    public Vector2d getClosestPointOnPath(Vector2d otherPos) {
        double minDist = Double.POSITIVE_INFINITY;
        Vector2d closest = null;
        for (PathSegment seg : segments) {
            Vector2d localClosest = seg.getClosestPoint(otherPos);
            double mag = localClosest.subtract(otherPos).getMagnitude();
            if (mag < minDist) {
                closest = localClosest;
                minDist = mag;
            }
        }
        return closest;
    }

    /**
     * 
     * @param otherPos
     *            - an (x,y) position, probably not on the path
     * @return the closest segment of the closest point to {@code otherPos} that
     *         is on the path
     */
    public PathSegment getClosestSegment(Vector2d otherPos) {
        double minDist = Double.POSITIVE_INFINITY;
        PathSegment closest = null;
        for (PathSegment seg : segments) {
            Vector2d localClosest = seg.getClosestPoint(otherPos);
            double mag = localClosest.subtract(otherPos).getMagnitude();
            if (mag < minDist) {
                closest = seg;
                minDist = mag;
                
            }
        }
        return closest;
    }
    /**
     * 
     * @param point - point we want to advance (if not on path, closest point on path is used)
     * @param lookahead - distance to move the point forward along the path
     * @return the advanced point. Will return the endpoint if lookahead spills over.
     */
    public Vector2d advancePoint(Vector2d point, double lookahead) {
        double pos = getLengthByPosition(point);
        pos += lookahead;
        return getPositionByLength(pos);
    }
}
