package Library4997;

import com.qualcomm.robotcore.hardware.HardwareMap;

import Library4997.MasqMotors.MasqMotor;
import Library4997.MasqResources.MasqHelpers.MasqHardware;
import Library4997.MasqSensors.MasqAdafruitIMU;

/**
 * Created by Archishmaan Peyyety on 8/9/18.
 * Project: MasqLib
 */

public abstract class MasqPositionTracker implements MasqHardware {
    private MasqMotor xSystem, ySystem;
    public MasqAdafruitIMU imu;
    private double globalX = 0, globalY = 0, prevX = 0, prevY = 0, xRadius = 0, yRadius = 0;

    public enum DeadWheelPosition {
        BOTH_CENTER, BOTH_PERPENDICULAR
    }

    public MasqPositionTracker(MasqMotor xSystem, MasqMotor ySystem, HardwareMap hardwareMap) {
        this.xSystem = xSystem;
        this.ySystem = ySystem;
        imu = new MasqAdafruitIMU("imu", hardwareMap);
        reset();
    }
    public MasqPositionTracker(MasqMotor xSystem, MasqMotor ySystem, String imuName, HardwareMap hardwareMap) {
        this.xSystem = xSystem;
        this.ySystem = ySystem;
        imu = new MasqAdafruitIMU(imuName, hardwareMap);
        reset();
    }
    public double getHeading () {
        return imu.getRelativeYaw();
    }
    public void updateSystem (DeadWheelPosition position) {
        switch (position) {
            case BOTH_CENTER: bothCenter(); break;
            case BOTH_PERPENDICULAR: bothPerpendicular(); break;
            default: break;
        }
    }

    public void reset() {
        xSystem.resetEncoder();
        ySystem.resetEncoder();
        imu.reset();
    }

    private void bothCenter() {
        double deltaX = (getXPosition() - prevX);
        double deltaY = (getYPosition() - prevY);
        double heading = Math.toRadians(getHeading());
        double x = deltaX * Math.cos(heading) - deltaY * Math.sin(heading);
        double y = deltaX * Math.sin(heading) + deltaY * Math.cos(heading);
        globalX += x;
        globalY += y;
        prevY = getXPosition();
        prevX = getYPosition();
    }

    private void bothPerpendicular() {
        double dHeading = getDHeading();
        double angularComponentX = xRadius * Math.PI * 2 * (dHeading / 360);
        double angularComponentY = yRadius * Math.PI * 2 * (dHeading / 360);
        double deltaX = (getXPosition() - prevX) - angularComponentX;
        double deltaY = (getYPosition() - prevY) - angularComponentY;
        double heading = Math.toRadians(getHeading());
        double x = deltaX * Math.cos(heading) - deltaY * Math.sin(heading);
        double y = deltaX * Math.sin(heading) + deltaY * Math.cos(heading);
        globalX += x;
        globalY += y;
        prevY = getXPosition();
        prevX = getYPosition();
    }
    //TODO: Fill this in
    public double getDHeading() {
        return 0;
    }

    public double getGlobalX() {
        return globalX * ((2 * Math.PI) / 1440);
    }
    public double getGlobalY() {
        return globalY * ((2 * Math.PI) / 1440);
    }

    public abstract double getXPosition();
    public abstract double getYPosition();

    public void setXRadius(double xRadius) {
        this.xRadius = xRadius;
    }

    public void setYRadius(double yRadius) {
        this.yRadius = yRadius;
    }

    @Override
    public String getName() {
        return "Tracker";
    }

    @Override
    public String[] getDash() {
        return new String[] {
            getName() +
            "Globalx: " + globalX +
            "Globaly: " + globalY
        };
    }
}