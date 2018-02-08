package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.mathUtil.RigidTransformation2d;
import org.rivierarobotics.mathUtil.Vector2d;

public class PurePursuitController {
    
    private Path path;
    private final double lookahead;
    
    public PurePursuitController(Path p, double look) {
        path = p;
        lookahead = look;
    }
    
    public class Arc{
        public final Vector2d center;
        public final double radius;
        public final double angle;
        public final double arcLength;
        
        public Arc(Vector2d cent, double rad, double ang) {
            center = cent;
            radius = rad;
            angle = ang;
            arcLength = ang*rad;
        }
    }
    
    
    public Arc getArcToPoint(RigidTransformation2d pose, Vector2d target) {
        Vector2d diff = target.subtract(pose.getTranslation());
        Vector2d midpoint = pose.getTranslation().add(diff.scale(.5));
        double normalAngle = midpoint.getNormal().getAngle();
        double normalFromPose = pose.getTranslation().getNormal().getAngle();
        RigidTransformation2d perpBisector = new RigidTransformation2d(midpoint, normalAngle);
        RigidTransformation2d robotToCenter = new RigidTransformation2d(pose.getTranslation(), normalFromPose);
        Vector2d centerPoint = perpBisector.getIntersection(robotToCenter);
        double radius = centerPoint.subtract(target).getMagnitude();
        double ang = Math.abs(centerPoint.subtract(target).getAngle() - centerPoint.subtract(pose.getTranslation()).getAngle());
        return new Arc(centerPoint, radius, ang);
    }
    
    public Arc
    
    
    
}
