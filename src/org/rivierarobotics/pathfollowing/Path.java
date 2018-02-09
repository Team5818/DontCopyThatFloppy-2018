package org.rivierarobotics.pathfollowing;

import java.util.ArrayList;

import org.rivierarobotics.mathUtil.RigidTransformation2d;
import org.rivierarobotics.mathUtil.Vector2d;

public class Path {

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

    private ArrayList<Waypoint> waypoints;
    private ArrayList<RigidTransformation2d> waypointsRT2D;

    public Path(ArrayList<Waypoint> wp, double rad) {
        waypoints = wp;
        calculatePathLength();
        calculateRigidTransforms();
    }

    private void calculatePathLength() {
        double totalLength = 0;
        waypoints.get(0).setDistance(0);
        for (int i = 0; i < waypoints.size() - 1; i++) {
            totalLength += waypoints.get(i + 1).getPosition().subtract(waypoints.get(i).getPosition()).getMagnitude();
            waypoints.get(i + 1).setDistance(totalLength);
        }
    }

    private void calculateRigidTransforms() {
        waypointsRT2D = new ArrayList<RigidTransformation2d>(waypoints.size() - 1);
        for (int i = 0; i < waypoints.size() - 1; i++) {
            RigidTransformation2d trans =
                    new RigidTransformation2d(waypoints.get(i).getPosition(), waypoints.get(i + 1).getPosition());
            waypointsRT2D.set(i, trans);
        }
    }

    /**
     * 
     * @param distance
     *            to interpolate along the path
     * @return (x,y) point that is @pos units along the path
     */
    public Vector2d interpolatePosition(double distAlongPath) {
        int nextPointIdx = 0;
        for (int i = 0; i < waypoints.size(); i++) {
            if (waypoints.get(i).getDistanceAlongPath() > distAlongPath) {
                nextPointIdx = i;
                break;
            }
        }
        Vector2d segment =
                waypoints.get(nextPointIdx).getPosition().subtract(waypoints.get(nextPointIdx - 1).getPosition());
        double distDiff = waypoints.get(nextPointIdx).getDistanceAlongPath() - distAlongPath;
        return waypoints.get(nextPointIdx).getPosition().subtract(segment.normalize(distDiff));
    }

    public Vector2d getClosestPointOnPath(Vector2d otherPos) {
        double minDist = Double.POSITIVE_INFINITY;
        Vector2d closest = new Vector2d(Double.NaN, Double.NaN);
        for (RigidTransformation2d wp : waypointsRT2D) {
            RigidTransformation2d posToPath = new RigidTransformation2d(otherPos, wp.getNormalToRotation());
            Vector2d intersect = posToPath.getIntersection(wp);
            double shortestLocalDistance = intersect.subtract(otherPos).getMagnitude();
            if (shortestLocalDistance < minDist) {
                minDist = shortestLocalDistance;
                closest = intersect;
            }
        }
        return closest;
    }

    public boolean isPointOnPath(Vector2d point) {
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Vector2d beg = waypoints.get(i).getPosition();
            Vector2d end = waypoints.get(i + 1).getPosition();
            if (point.subtract(beg).getSlope() == end.subtract(point).getSlope() && point.getX() < end.getX()
                    && point.getY() < end.getY() && point.getX() > beg.getX() && point.getY() > beg.getY()) {
                return true;
            }
        }
        return false;
    }
    
    public Vector2d advancePoint(Vector2d point, double slideDistance) {
        
    }
}
