package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.mathUtil.RigidTransformation2d;
import org.rivierarobotics.mathUtil.Vector2d;

public class PurePursuitController {
    
    private Path path;
    
    
    public Vector2d getArcCenter(RigidTransformation2d pose, Vector2d target) {
        Vector2d diff = target.subtract(pose.getTranslation());
        Vector2d midpoint = pose.getTranslation().add(diff.scale(.5));
        double normalAngle = midpoint.getNormal().getAngle();
        double normalFromPose = pose.getTranslation().getNormal().getAngle();
        RigidTransformation2d perpBisector = new RigidTransformation2d(midpoint, normalAngle);
        RigidTransformation2d robotToCenter = new RigidTransformation2d(pose.getTranslation(), normalFromPose);
        Vector2d centerPoint = perpBisector.getIntersection(robotToCenter);
        return centerPoint;
    }
    
    public double getArcRadius(RigidTransformation2d pose, Vector2d target) {
        Vector2d center = getArcCenter(pose, target);
        double radius = center.subtract(target).getMagnitude();
        return radius;
    }
    
    public double getArcDistance(RigidTransformation2d pose, Vector2d target) {
        
    }
    
}
