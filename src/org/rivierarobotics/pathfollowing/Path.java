package org.rivierarobotics.pathfollowing;

import java.util.ArrayList;

import org.rivierarobotics.mathUtil.RigidTransformation2d;
import org.rivierarobotics.mathUtil.Vector2d;

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
        endPoint = segments.get(numSegs).getEnd();
        calculatePathLength();
    }

    private void calculatePathLength() {
        double totalLength = 0;
        for (PathSegment seg : segments) {
            totalLength += seg.getLength();
        }
        pathLength = totalLength;
    }

    /**
     * 
     * @param distance
     *            to interpolate along the path
     * @return (x,y) point that is @pos units along the path
     */
    public Vector2d interpolatePosition(double distAlongPath) {
        int totalDistance = 0;
        for (int i = 0; i < numSegs; i++) {
            if (totalDistance <= distAlongPath && distAlongPath <= totalDistance + segments.get(i).getLength()) {
                return segments.get(i).getPositionByLength(distAlongPath - totalDistance);
            }
            totalDistance += segments.get(i).getLength();
        }
        return (totalDistance < 0 ? startPoint : endPoint);
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
            if (localClosest.subtract(otherPos).getMagnitude() < minDist) {
                closest = localClosest;
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
            if (localClosest.subtract(otherPos).getMagnitude() < minDist) {
                closest = seg;
            }
        }
        return closest;
    }
    /**
     * 
     * @param point - point we want to advance
     * @param lookahead - distance to move the point forward along the path
     * @return the advanced point. Will return the endpoint if lookahead spills over.
     */
    public Vector2d advancePoint(Vector2d point, double lookahead) {
        Vector2d closestPoint = getClosestPointOnPath(point);
        PathSegment closestSeg = getClosestSegment(point);
        double advanced = closestSeg.advancePoint(closestPoint, lookahead);
        for (int i = segments.indexOf(getClosestSegment(point)); i < numSegs; i++) {
            PathSegment currSeg = segments.get(i);
            if (advanced < currSeg.getLength()) {
                return currSeg.getPositionByLength(advanced);
            }
            advanced = advanced - currSeg.getLength();
        }
        return endPoint;
    }
}
