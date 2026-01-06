package frc.robot.commands.transitions;

import ControlAnnotations.Transition;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.robotControl.RobotControl;
import frc.robot.utils.RobotStates;

@Transition
public class TransitTransition extends SequentialCommandGroup {

    private enum TransitionType {
        BASIC,
    }

    private static class BasicTransition extends SequentialCommandGroup {
        public BasicTransition() {
            // Add commands for basic transition here
        }
    }

    public TransitTransition() {
        addCommands(
                new SelectCommand<TransitionType>(
                        java.util.Map.of(
                                TransitionType.BASIC, new BasicTransition()
                        ),
                        this::chooseTransitionType
                ),
                new InstantCommand(() -> {
                    RobotControl.setCurrentMode(RobotStates.transit);
                })
        );
    }

    private TransitionType chooseTransitionType() {
        return TransitionType.BASIC;
    }
}
