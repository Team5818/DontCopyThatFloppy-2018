package org.rivierarobotics.autos.twocubeleftscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class CubeToScale2L extends SideDependentTrajectoryExecutor{
    
    public static final double OFFSET_X_RIGHT = MathUtil.feet2inches(20);
    public static final double OFFSET_Y_RIGHT = MathUtil.feet2inches(19.5);

    public static final Waypoint[] LEFT_PATH = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(MathUtil.feet2inches(25) - OFFSET_X_RIGHT, MathUtil.feet2inches(22) - OFFSET_Y_RIGHT, Pathfinder.d2r(60))};
    
    public static final Waypoint[] RIGHT_PATH = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(36, 0, 0)};

    public CubeToScale2L() {
        requires(Robot.runningRobot.driveTrain);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, true, -180, DriveGear.GEAR_HIGH);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, true, -180, DriveGear.GEAR_HIGH);
        
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}
