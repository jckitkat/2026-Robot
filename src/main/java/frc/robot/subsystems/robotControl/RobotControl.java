package frc.robot.subsystems.robotControl;

import RobotController.RobotController;
import frc.robot.commands.driveStates.TeleopDrive;
import org.littletonrobotics.junction.Logger;

public class RobotControl extends RobotController implements RobotControlIO{
    private final RobotControlIOInputsAutoLogged inputs = new RobotControlIOInputsAutoLogged();
    private final RobotControlIO io = this;

    @Override
    public void periodic() {
        updateInputs(inputs);
        Logger.processInputs("RobotControl", inputs);
    }

    @Override
    public void updateInputs(RobotControlIOInputs inputs) {
        inputs.currentCommand = currentMode.getName();
        inputs.previousCommand = previousMode.getName();
        inputs.currentDriveCommand = currentDriveMode.getName();
        inputs.previousDriveCommand = previousDriveMode.getName();
        inputs.currentCommandRunning = currentMode.isScheduled();
    }
}
