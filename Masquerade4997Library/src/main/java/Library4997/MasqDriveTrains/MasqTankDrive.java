package Library4997.MasqDriveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Library4997.MasqMotors.MasqMotorSystem;
import Library4997.MasqUtilities.MasqHardware;
import Library4997.MasqUtilities.MasqUtils;

/**
 * Created by Archish on 10/28/16.
 */
public class MasqTankDrive implements MasqHardware {
    public MasqMotorSystem leftDrive, rightDrive = null;
    private double destination = 0;
    public MasqTankDrive(String name1, String name2, String name3, String name4, HardwareMap hardwareMap) {
        leftDrive = new MasqMotorSystem(name1, DcMotor.Direction.REVERSE, name2, DcMotor.Direction.REVERSE, "LEFTDRIVE", hardwareMap);
        rightDrive = new MasqMotorSystem(name3, DcMotor.Direction.FORWARD, name4, DcMotor.Direction.FORWARD, "RIGHTDRIVE", hardwareMap);
    }
    public MasqTankDrive(HardwareMap hardwareMap){
        leftDrive = new MasqMotorSystem("leftFront", DcMotor.Direction.REVERSE, "leftBack", DcMotor.Direction.REVERSE, "LEFTDRIVE", hardwareMap);
        rightDrive = new MasqMotorSystem("rightFront", DcMotor.Direction.FORWARD, "rightBack", DcMotor.Direction.FORWARD, "RIGHTDRIVE", hardwareMap);
    }
    public void resetEncoders () {
        leftDrive.resetEncoder();
        rightDrive.resetEncoder();
    }
    public void setPower (double leftPower, double rightPower) {
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
    public void setPower(double power){
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }
    public void setKp(double kp){
        leftDrive.setKp(kp);
        rightDrive.setKp(kp);
    }
    public void setKi(double ki){
        leftDrive.setKi(ki);
        rightDrive.setKi(ki);
    }
    public void setKd(double kd){
        leftDrive.setKd(kd);
        rightDrive.setKd(kd);
    }
    public double getRate() {
        return (leftDrive.getRate() + rightDrive.getRate())/2;
    }
    public double getPower() {
        return (leftDrive.getPower() + rightDrive.getPower()) /2;
    }
    public void setPowerLeft (double power) {
        leftDrive.setPower(power);
    }
    public void setPowerRight (double power) {
        rightDrive.setPower(power);
    }
    public void runUsingEncoder() {
        leftDrive.runUsingEncoder();
        rightDrive.runUsingEncoder();
    }
    public void setClosedLoop(boolean closedLoop){
        leftDrive.setClosedLoop(closedLoop);
        rightDrive.setClosedLoop(closedLoop);
    }

    public void runWithoutEncoders() {
        leftDrive.runUsingEncoder();
        rightDrive.runUsingEncoder();
    }
    public void setEncoderCounts(double counts) {
        leftDrive.setEncoderCounts(counts);
        rightDrive.setEncoderCounts(counts);
    }
    public void stopDriving() {
        setPower(0,0);
    }
    private boolean opModeIsActive() {
        return MasqUtils.opModeIsActive();
    }
    public void zeroPowerBehavior(){
        rightDrive.breakMotors();
    }
    public double getCurrentPosition() {
        return (leftDrive.getCurrentPosition() + rightDrive.getCurrentPosition())/2;
    }
    public double getAbsolutePositon () {
        return (leftDrive.getAbsolutePosition() + rightDrive.getAbsolutePosition())/2;
    }
    public String getName() {
        return "DRIVETRAIN";
    }
    public String[] getDash() {
        return new String[]{ "Rate "+ getRate()};
    }
}