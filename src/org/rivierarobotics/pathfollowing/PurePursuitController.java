package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.constants.Rotation;
import org.rivierarobotics.mathUtil.MathUtil;
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
        public final Vector2d start;
        public final Vector2d end;
        public final Rotation direction;
        
        public Arc(Vector2d cent, double rad, double ang, Vector2d s, Vector2d e, Rotation r) {
            center = cent;
            radius = rad;
            angle = ang;
            arcLength = ang*rad;
            start = s;
            end = e;
            direction = r;
        }
    }
    
    /**
     * 
     * @param pose - the robot pose
     * @param target - the point we want to arc to 
     * @return The arc that gets us there
     */
    public Arc getArcToPoint(RigidTransformation2d pose, Vector2d target) {
        Vector2d diff = target.subtract(pose.getTranslation());
        Vector2d midpoint = pose.getTranslation().add(diff.scale(.5));
        double normalAngle = diff.getNormal().getAngle();
        double normalFromPose = pose.getNormalToRotation();
        RigidTransformation2d perpBisector = new RigidTransformation2d(midpoint, normalAngle);
        RigidTransformation2d robotToCenter = new RigidTransformation2d(pose.getTranslation(), normalFromPose);
        Vector2d centerPoint = perpBisector.getIntersection(robotToCenter);
        double radius = centerPoint.subtract(pose.getTranslation()).getMagnitude();
        double ang = Math.abs(centerPoint.subtract(target).getAngle() - centerPoint.subtract(pose.getTranslation()).getAngle());
        Vector2d radiusVec = pose.getTranslation().subtract(centerPoint);
        
        double x1 = Math.cos(pose.getRotation());
        double y1 = Math.sin(pose.getRotation());
        
        double x2 = radiusVec.getX();
        double y2 = radiusVec.getY();
        
        //Directionality test
        if(x1*y2 > x2*y1) {
            return new Arc(centerPoint, radius, ang, pose.getTranslation(), target, Rotation.CLOCKWISE);

        }
        else {
            return new Arc(centerPoint, radius, ang, pose.getTranslation(), target, Rotation.COUNTER_CLOCKWISE);

        }
    }
    
    /**
     * 
     * @param pose - the pose of the robot
     * @return - the arc that will get us to the path
     */
    public Arc getArcToPath(RigidTransformation2d pose) {
        Vector2d target = path.advancePoint(pose.getTranslation(), lookahead);
        return getArcToPoint(pose, target);
    }
    
}
