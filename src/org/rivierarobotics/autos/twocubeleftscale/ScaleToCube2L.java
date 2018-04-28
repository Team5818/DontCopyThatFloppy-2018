package org.rivierarobotics.autos.twocubeleftscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.Vector2d;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class ScaleToCube2L extends SideDependentTrajectoryExecutor {

    public static final double OFFSET_X_RIGHT = MathUtil.feet2inches(25);
    public static final double OFFSET_Y_RIGHT = MathUtil.feet2inches(22);
    private static Vector2d target = new Vector2d(MathUtil.feet2inches(20),MathUtil.feet2inches(19.2));
    private static Vector2d diffVec = target.subtract(new Vector2d(OFFSET_X_RIGHT, OFFSET_Y_RIGHT));
    private static Vector2d rotated = diffVec.rotate(Pathfinder.d2r(120));
    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(rotated.getX(), rotated.getY(), Pathfinder.d2r(-60))};
    
    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(2.18), 0, 0)};

    public ScaleToCube2L() {
        requires(Robot.runningRobot.driveTrain);
        rightExecutor =  new TrajectoryExecutor(RIGHT_PATH, false, 180, DriveGear.GEAR_HIGH);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, false, 120, DriveGear.GEAR_HIGH);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}