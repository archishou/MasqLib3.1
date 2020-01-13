package org.firstinspires.ftc.teamcode.Robots.MarkOne.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.MarkOne;

import java.util.ArrayList;
import java.util.List;

import Library4997.MasqControlSystems.MasqPurePursuit.MasqWayPoint;
import Library4997.MasqResources.MasqHelpers.Direction;
import Library4997.MasqResources.MasqMath.MasqPoint;
import Library4997.MasqSensors.MasqClock;
import Library4997.MasqWrappers.MasqLinearOpMode;

import static org.firstinspires.ftc.teamcode.Robots.MarkOne.Robot.SubSystems.CVInterpreter.SkystonePosition;

/**
 * Created by Keval Kataria on 1/4/2020
 */
@Autonomous(name = "Blue Stone v2", group = "MarkOne")
public class BlueStoneAutov2 extends MasqLinearOpMode{
    private MarkOne robot = new MarkOne();
    private SkystonePosition position;
    private MasqClock timeoutClock;
    private List<MasqPoint> stones = new ArrayList<>();
    private MasqPoint
            bridge = new MasqPoint(-25,15,90),
            grabFoundation = new MasqPoint(-88,34,90),
            rotation = new MasqPoint(-84,28,179);
    private MasqWayPoint
            bridge1 = new MasqWayPoint(new MasqPoint(-25,21,90),5,0.9),
            bridge2 = new MasqWayPoint(new MasqPoint(-59,21,90),3,0.7),
            foundation = new MasqWayPoint(new MasqPoint(-90,31,90),4,0.4);
    @Override
    public void runLinearOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.initializeAutonomous();
        stones.add(null);

        stones.add(new MasqPoint(-17,30,90));
        stones.add(new MasqPoint(-7,29,90));
        stones.add(new MasqPoint(0,29,90));

        stones.add(new MasqPoint(6,30,90));
        stones.add(new MasqPoint(12,30,90));
        stones.add(new MasqPoint(22,30,90));
        // robot.cv.start();

        while(!opModeIsActive()) {
            dash.create("Hello");
            dash.update();
        }

        waitForStart();
        robot.sideGrabber.rightUp(0);
        robot.sideGrabber.leftUp(0);
        robot.sideGrabber.rightOpen(0);
        robot.sideGrabber.leftClose(0);
        /*if (position == LEFT) runSimultaneously(() -> mainAuto(stones.get(1), stones.get(4));,() -> robot.cv.stop());
        else if (position == MIDDLE) runSimultaneously(() -> runStoneMiddle(),() -> robot.cv.stop());
        else runSimultaneously(() -> runStoneRight(),() -> robot.cv.stop());*/
        mainAuto(stones.get(3), stones.get(6));
    }
    private void mainAuto(MasqPoint stone1, MasqPoint stone2) {
        robot.gotoXY(stone1);
        robot.sideGrabber.rightDown(1);
        robot.sideGrabber.rightClose(1);
        robot.sideGrabber.rightMid(0);
        robot.xyPath(robot.getCurrentWayPoint(), bridge1, bridge2, foundation);
        robot.sideGrabber.rightLowMid(0);
        robot.sideGrabber.rightOpen(0);
        robot.xyPath(robot.getCurrentWayPoint(),bridge2,bridge1,new MasqWayPoint(stone2,0.5,0));
        robot.sideGrabber.rightDown(1);
        robot.sideGrabber.rightClose(1);
        robot.sideGrabber.rightMid(0);
        robot.xyPath(robot.getCurrentWayPoint(),bridge1,bridge2,foundation);
        robot.sideGrabber.rightLowMid(0);
        robot.sideGrabber.rightOpen(0);
        sleep();
        robot.turnRelative(90, Direction.LEFT);
        robot.gotoXY(robot.tracker.getGlobalX(), robot.tracker.getGlobalY() + 7,
                robot.tracker.getHeading(), 2, 0.5, 1);
        robot.gotoXY(new MasqPoint(robot.tracker.getGlobalX()-6,robot.tracker.getGlobalY(),
                robot.tracker.getHeading()),2,0.5,1);
        robot.foundationHook.lower();
        sleep(1);
        robot.gotoXY(new MasqPoint(robot.tracker.getGlobalX(), robot.tracker.getGlobalY()-20,robot.tracker.getHeading()));
        robot.gotoXY(new MasqPoint(-60, 0, -90),2,0.5,0.5);
        robot.foundationHook.raise();
        robot.gotoXY(new MasqPoint(-84, 24, -90));
        robot.gotoXY(new MasqPoint(-34, 24, -90));
    }
    private void runStoneMiddle() {}
    private void runStoneRight() {}
}