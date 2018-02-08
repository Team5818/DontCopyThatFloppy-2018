package org.rivierarobotics.pathfollowing;

import java.util.ArrayList;

import org.rivierarobotics.mathUtil.Vector2d;

public class Path {
    
    private enum InterpolationMethod{
        LINEAR_SHARP, LINEAR_ROUNDED
    }
    
    public class Arc{
        Vector2d point1;
        Vector2d point2;
        double radius;
        
        public Arc(Vector2d p1, Vector2d p2, double rad) {
            point1 = p1;
            point2 = p2;
            radius = rad;
        }
        
        public Vector2d interpolatePosition(double t) {
            
        }
    }
    
    private ArrayList<Vector2d> waypoints;
    private double arcRadius;
    
    public Path(ArrayList<Vector2d> wp, double rad) {
        waypoints = wp;
        arcRadius = rad;
    }
    
    public double getPathLength() {
        double totalLength = 0;
        for(int i = 0; i < waypoints.size() - 1; i++) {
            totalLength += waypoints.get(i+1).subtract(waypoints.get(i)).getMagnitude();
        }
        return totalLength;
    }
    
    public Vector2d interpolatePosition(double t, InterpolationMethod meth) {
        switch(meth) {
            case LINEAR_SHARP:
                
        }
    }
}
