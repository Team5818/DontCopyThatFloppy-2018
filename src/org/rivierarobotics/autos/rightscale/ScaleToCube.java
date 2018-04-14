package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.Vector2d;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class ScaleToCube extends SideDependentTrajectoryExecutor {

    public static final double OFFSET_X_RIGHT = MathUtil.feet2inches(24);
    public static final double OFFSET_Y_RIGHT = MathUtil.feet2inches(5);
    public static final double THETA_OFFSET = Pathfinder.d2r(120.0);
    private static Vector2d start = new Vector2d(MathUtil.feet2inches(18),MathUtil.feet2inches(7.5));
    private static Vector2d diffVec = start.subtract(new Vector2d(OFFSET_X_RIGHT, OFFSET_Y_RIGHT));
    private static Vector2d rotated = diffVec.rotate(Pathfinder.d2r(-120));
    private static Vector2d target = rotated.add(start);
    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(target.getX(), target.getY(), Pathfinder.d2r(180) - THETA_OFFSET)};

    public static final Waypoint[] LEFT_PATH =  
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(0, MathUtil.feet2inches(3), 0)};
;

    public ScaleToCube() {
        requires(Robot.runningRobot.driveTrain);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, true, 120);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, true, -180);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}