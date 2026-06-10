package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;
// import com.revrobotics.spark.ClosedLoopSlot;
// import com.revrobotics.spark.SparkBase.ControlType;
// import com.revrobotics.REVLibError;
// import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants.ShooterConstants;
import frc.robot.Configs;
import org.littletonrobotics.junction.Logger;

public class Shooter extends SubsystemBase {

    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex ShooterLeft1Motor = new SparkFlex(ShooterConstants.SHOOTER_L1_ID, MotorType.kBrushless);
    private SparkClosedLoopController ShooterLeft1Controller = ShooterLeft1Motor.getClosedLoopController();

    private SparkFlex ShooterLeft2Motor = new SparkFlex(ShooterConstants.SHOOTER_L2_ID, MotorType.kBrushless);

    private SparkFlex ShooterRight1Motor = new SparkFlex(ShooterConstants.SHOOTER_R1_ID, MotorType.kBrushless);

    private SparkFlex ShooterRight2Motor = new SparkFlex(ShooterConstants.SHOOTER_R2_ID, MotorType.kBrushless);
  

    public Shooter() {
        ShooterLeft1Motor.configure(Configs.ShooterSubsystem.ShooterMotorLeft1Config, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        ShooterLeft2Motor.configure(Configs.ShooterSubsystem.ShooterMotorLeft2Config, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        ShooterRight1Motor.configure(Configs.ShooterSubsystem.ShooterMotorRight1Config, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        ShooterRight2Motor.configure(Configs.ShooterSubsystem.ShooterMotorRight2Config, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT SHOOTER MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void runRPM(double rpm) {
        ShooterLeft1Controller.setSetpoint(rpm,
                ControlType.kMAXMotionVelocityControl);
    }

    public void runShooter() {
        ShooterLeft1Controller.setSetpoint(ShooterConstants.DRIBBLE_SPEED,
                ControlType.kMAXMotionVelocityControl);
    }

    public void stopShooter() {
        desiredPercent = 0.0;
        ShooterLeft1Motor.set(0);
    }

    public Command runShooterCommand() {
        return new RunCommand(() -> runShooter(), this)
                .finallyDo(interrupted -> stopShooter());
    }

    public Command stopShooterCommand() {
        return new RunCommand(() -> stopShooter(), this);
    }

    public Command runDefaultCommand()
    {
        return stopShooterCommand();
    }

    @Override
    public void periodic() {
        // AdvantageKit Logging
        // Commanded shooter motor percent output.
        double ShooterLeft1RPM = ShooterLeft1Motor.getEncoder().getVelocity();
        double ShooterLeft2RPM = ShooterLeft2Motor.getEncoder().getVelocity();
        double ShooterRight1RPM = ShooterRight1Motor.getEncoder().getVelocity();
        double ShooterRight2RPM = ShooterRight2Motor.getEncoder().getVelocity();

        Logger.recordOutput("Shooter/DesiredPercent", desiredPercent);
        // Applied voltage to shooter motor.
        Logger.recordOutput("Shooter/AppliedVolts", ShooterLeft1Motor.getAppliedOutput() * ShooterLeft1Motor.getBusVoltage());
        Logger.recordOutput("ShooterLeft1RPM", ShooterLeft1RPM);
        Logger.recordOutput("ShooterLeft2RPM", ShooterLeft2RPM);
        Logger.recordOutput("ShooterRight1RPM", ShooterRight1RPM);
        Logger.recordOutput("ShooterRight2RPM", ShooterRight2RPM);
        Logger.recordOutput("ShooterTargetRPM", ShooterConstants.DRIBBLE_SPEED);


    }
}