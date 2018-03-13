package org.rivierarobotics.autos.centerswitch;

import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveToSwitchFromCenter extends Command {
    
    public static final double X_OFFSET = 0;
    public static final double Y_OFFSET = MathUtil.feet2inches(12.21);

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(2) - X_OFFSET, MathUtil.feet2inches(12.21) - Y_OFFSET, 0),
                    new Waypoint(MathUtil.feet2inches(6) - X_OFFSET, MathUtil.feet2inches(15) - Y_OFFSET, Pathfinder.d2r(60)),
                    new Waypoint(MathUtil.feet2inches(9) - X_OFFSET, MathUtil.feet2inches(18) - Y_OFFSET, 0),
                    new Waypoint(MathUtil.feet2inches(10.33) - X_OFFSET, MathUtil.feet2inches(18) - Y_OFFSET, 0)
            };

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] {
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(2) - X_OFFSET, MathUtil.feet2inches(12.21) - Y_OFFSET, 0),
                    new Waypoint(MathUtil.feet2inches(5) - X_OFFSET, MathUtil.feet2inches(10) - Y_OFFSET, Pathfinder.d2r(-45)),
                    new Waypoint(MathUtil.feet2inches(8.5) - X_OFFSET,  MathUtil.feet2inches(9) - Y_OFFSET, 0),
                    new Waypoint(MathUtil.feet2inches(10.33) - X_OFFSET, MathUtil.feet2inches(9) - Y_OFFSET, 0)
            };

    private TrajectoryExecutor leftEx;
    private TrajectoryExecutor rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public DriveToSwitchFromCenter() {
        requires(Robot.runningRobot.driveTrain);
        leftEx = new TrajectoryExecutor(LEFT_PATH, false,0);
        rightEx = new TrajectoryExecutor(RIGHT_PATH, false,0);
    }

    @Override
    protected void initialize() {
        fieldData = Robot.runningRobot.getSide();
        if (fieldData[0] == Side.LEFT) {
            selectedEx = leftEx;
        } else {
            selectedEx = rightEx;
        }
        selectedEx.start();
    }

    @Override
    protected boolean isFinished() {
        return selectedEx.isFinished();
    }
    
    @Override
    protected void interrupted() {
        selectedEx.stop();
    }
}
