package frc.robot.subsystems.robotControl;

import org.littletonrobotics.junction.AutoLog;

public interface RobotControlIO {
    @AutoLog
    public static class RobotControlIOInputs {
        public String currentCommand = "";
        public String previousCommand = "";
        public String currentDriveCommand = "";
        public String previousDriveCommand = "";
        public boolean currentCommandRunning = false;
    }

    public void updateInputs(RobotControlIOInputs inputs);

}
