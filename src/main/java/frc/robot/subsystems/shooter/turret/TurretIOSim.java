package frc.robot.subsystems.shooter.turret;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;
import frc.robot.Robot;

public class TurretIOSim implements TurretIO{
    private final DCMotor gearbox = DCMotor.getKrakenX44Foc(1);
    private final DCMotorSim sim =
            new DCMotorSim(LinearSystemId.createDCMotorSystem(gearbox, 0.001, 1), gearbox);

    private double rotationTarget = 0;

    private PIDController pid = new PIDController(Constants.Shooter.Turret.simP, Constants.Shooter.Turret.simI, Constants.Shooter.Turret.simD);

    public TurretIOSim() {}

    @Override
    public void updateInputs(TurretIOInputs inputs) {
        sim.setInputVoltage(MathUtil.clamp(pid.calculate(sim.getAngularPositionRad()), -12.0, 12.0));
        sim.update(Constants.loopPeriodSecs);

        inputs.targetRotation = this.rotationTarget;
        inputs.currentAmperage = sim.getCurrentDrawAmps();
        inputs.currentRotation = sim.getAngularPositionRad();
        inputs.currentTorque = sim.getTorqueNewtonMeters();
        inputs.atLeftLimit = Robot.drivetrain.getRotation().getRadians() - sim.getAngularPositionRad() >= Math.PI;
        inputs.atRightLimit = Robot.drivetrain.getRotation().getRadians() - sim.getAngularPositionRad() <= -Math.PI;
    };

    @Override
    public void setRotation(double targetRotation) {
        rotationTarget = targetRotation;
        if (targetRotation >= Constants.Shooter.Turret.leftLimit) {
            pid.setSetpoint(targetRotation - 0.01);
        } else if (targetRotation <= Constants.Shooter.Turret.rightLimit) {
            pid.setSetpoint(targetRotation + 0.01);
        } else {
            pid.setSetpoint(targetRotation);
        }
    };

    @Override
    public void setPower(double percentage) {};
}
