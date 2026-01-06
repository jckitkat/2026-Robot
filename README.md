# State Controlled Robot Template
**FRC 3767 TC Titans**

## Overview

This is a state machine-based robot code template for FRC (FIRST Robotics Competition) that combines WPILib's Command-Based framework with AdvantageKit logging and a custom annotation-driven state management system. The template features a swerve drive robot with an organized state machine architecture for controlling robot behaviors.

## Key Features

- **Feature Name**: Description about `this code`

## Project Structure

```
src/main/java/frc/robot/
├── commands/
│   ├── basicCommands/                     # Robot state commands (@State annotated)
│   │   └── exampleSubsystemCommand.java   # Example transit state
│   ├── states/                     # Robot state commands (@State annotated)
│   │   └── Transit.java           # Example transit state
│   ├── transitions/                # State transition commands (@Transition annotated)
│   │   └── TransitTransition.java # Example transition to transit state
│   └── driveStates/               # Drive mode commands
│       └── TeleopDrive.java       # Teleop drive command
├── subsystems/
│   ├── exampleSubsystem/          # Example additional subsystem
│   │   ├── ExampleSubsystem.java  # Example subsystem implementation
│   │   └── ExampleSubsystemIO.java# Example subsystem IO interface
│   └── robotControl/
│       ├── RobotControl.java     # State machine controller
│       └── RobotControlIO.java   # State logging interface
├── util/
│   ├── DrivetrainPublisher.java   # Drivetrain input management
```

## State Machine Implementation

### Core Concept

The robot uses a dual-layer command system:

1. **Robot State Commands**: High-level robot behaviors (intake, shoot, climb, etc.)
2. **Drive Mode Commands**: Independent drive control modes (teleop, auto-align, etc.)

Both can run simultaneously, allowing the robot to drive while performing other actions.

### State Machine Components

#### 1. States (`@State` annotation)

States represent discrete robot behaviors. Each state is a Command class marked with the `@State` annotation.

**Example: Transit State**
```java
@State
public class Transit extends Command {
    // State initialization and execution logic
}
```

#### 2. Transitions (`@Transition` annotation)

Transitions manage the logic for moving between states. They are SequentialCommandGroups that:
- Perform necessary actions before state change
- Update the current state in RobotControl
- Can select different transition paths based on robot conditions

**Example: Transit Transition**
```java
@Transition
public class TransitTransition extends SequentialCommandGroup {
    public TransitTransition() {
        addCommands(
            new SelectCommand<>(
                Map.of(TransitionType.BASIC, new BasicTransition()),
                this::chooseTransitionType
            ),
            new InstantCommand(() -> {
                RobotControl.setCurrentMode(RobotStates.transit);
            })
        );
    }
}
```

#### 3. RobotControl Subsystem

The `RobotControl` class extends `RobotController` (from the annotation processor library) and manages:
- Current and previous robot states
- Current and previous drive modes
- State transitions and command scheduling
- Logging of state machine data

**Key Methods:**
- `setCurrentMode()`: Changes the robot's state
- `setDriveModeCommand()`: Changes the drive mode
- `updateInputs()`: Logs state machine status to AdvantageKit

### Drive Commands

Drive commands are separate from robot state commands and control drivetrain behavior:

Drive commands set the control inputs to be read on each cycle of the drivetrain subsystem.

**TeleopDrive**: Field-centric manual control
```java
public class TeleopDrive extends Command {
    public TeleopDrive() {
        DrivetrainPublisher.setSuppliers(
            Robot.driverController::getLeftX,
            Robot.driverController::getLeftY,
            Robot.driverController::getRightX,
            () -> true  // Field-centric enabled
        );
    }
}
```

## AdvantageKit Integration

### Logging Modes

The robot supports three operating modes:

1. **REAL**: Runs on physical robot, logs to USB drive
2. **SIM**: Runs in simulation, logs to NetworkTables
3. **REPLAY**: Replays logged data for analysis and debugging

### IO Interfaces

All hardware interactions use IO interfaces for deterministic logging:
- `ModuleIO`: Swerve module interface (drive motor, steer motor, encoder)
- `GyroIO`: Gyroscope interface
- `RobotControlIO`: State machine logging interface

Each has multiple implementations (real hardware, simulation, replay).

## Building and Deploying

### Prerequisites
- WPILib 2025.3.2 or later
- Java 17
- Gradle (included via wrapper)

### Build Commands
```bash
# Build the code
./gradlew build

# Deploy to robot
./gradlew deploy

# Run simulation
./gradlew simulateJava

# Run log replay (watch mode)
./gradlew replayWatch
```

## Dependencies

- **WPILib**: FRC robot framework (Command-Based)
- **AdvantageKit**: Deterministic logging and replay
- **Phoenix 6**: CTRE motor controller library (TalonFX)
- **NavX/Pigeon2**: Gyroscope support
- **Custom Libraries**:
  - `robotControlAnnotations`: State machine annotation definitions (org.frc3767)
  - `annotationHandlers`: Annotation processing for state machine (org.frc3767)

## Getting Started

1. **Configure Robot**: Update `Constants.java` with your robot's parameters
2. **Define States**: Create state commands in `commands/states/` with `@State` annotation
3. **Create Transitions**: Add transition logic in `commands/transitions/` with `@Transition` annotation
4. **Initialize States**: Set up initial state in `Robot.java`
5. **Bind Controls**: Configure controller bindings in `RobotContainer.java`

## License

This project incorporates code from:
- **AdvantageKit** (GPL-3.0) - See `AdvantageKit-License.md`
- **WPILib** - See `WPILib-License.md`

Original template code by **FRC 3767 TC Titans**.

## Resources

- [WPILib Documentation](https://docs.wpilib.org/)
- [AdvantageKit Documentation](https://github.com/Mechanical-Advantage/AdvantageKit)
- [Phoenix 6 Documentation](https://pro.docs.ctr-electronics.com/)
- [FRC 3767 GitHub](https://github.com/frc3767)

