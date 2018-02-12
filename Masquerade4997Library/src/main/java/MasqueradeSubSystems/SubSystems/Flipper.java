package MasqueradeSubSystems.SubSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

import Library4997.MasqServos.MasqServo;
import Library4997.MasqUtilities.MasqHardware;
import Library4997.MasqWrappers.MasqController;
import MasqueradeSubSystems.SubSystem;

/**
 * Created by Archish on 2/12/18.
 */

public class Flipper implements SubSystem {
    private MasqServo flipperLeft;
    private MasqServo flipperRight;
    private List<MasqHardware> hardware = new ArrayList<>();

    public enum Position {
        OUT (new double[]{.05, .13}),
        IN (new double[]{.62, .46}),
        MID (new double[]{.5, .4}),
        RIGHT(new double[]{.27, .27});
        public final double[] pos;
        Position (double[] pos) {this.pos = pos;}
    }

    public Flipper (HardwareMap hardwareMap) {
        flipperLeft = new MasqServo("flipeft", hardwareMap);
        flipperRight = new MasqServo("flipRight", hardwareMap);
        hardware.add(flipperLeft);
        hardware.add(flipperRight);
    }

    public void setPosition (Position position) {
        flipperRight.setPosition(position.pos[0]);
        flipperLeft.setPosition(position.pos[1]);
    }

    @Override
    public void DriverControl(MasqController controller) {
        if (controller.rightStickY() < -.5) {
            setPosition(Position.OUT);
        }
        else if (controller.rightStickY() > .5) {
            setPosition(Position.IN);
        }
        else if (controller.rightStickX() > .5) {
            setPosition(Position.RIGHT);
        }
        else if (controller.rightStickX() < -.5) {
            setPosition(Position.MID);
        }
    }

    @Override
    public String getName() {
        return "Flipper";
    }

    @Override
    public List<MasqHardware> getComponents() {
        return hardware;
    }
}