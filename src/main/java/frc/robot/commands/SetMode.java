package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.robotControl.RobotControl;

public class SetMode extends Command {
    private Command mode;

    public SetMode(Command mode) {
        this.mode = mode;
    }

    @Override
    public void initialize() {
        RobotControl.setCurrentMode(mode);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
