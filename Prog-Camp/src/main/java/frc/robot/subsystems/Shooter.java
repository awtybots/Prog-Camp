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
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;

import frc.robot.Constants.ShooterConstants;
import frc.robot.Configs;
import org.littletonrobotics.junction.Logger;

public class Shooter extends SubsystemBase {

    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex ShooterLeftMotor = new SparkFlex(ShooterConstants.SHOOTER_L1_ID, MotorType.kBrushless);
    private SparkClosedLoopController ShooterLeftController = ShooterLeftMotor.getClosedLoopController();

    private SparkFlex ShooterRightMotor = new SparkFlex(ShooterConstants.SHOOTER_R1_ID, MotorType.kBrushless);
    private SparkClosedLoopController ShooterRightController = ShooterRightMotor.getClosedLoopController();
  

    public Shooter() {
        ShooterLeftMotor.configure(Configs.ShooterSubsystem.ShooterMotorLeftConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        ShooterRightMotor.configure(Configs.ShooterSubsystem.ShooterMotorRightConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT SHOOTER MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void runShooter() {
        ShooterLeftController.setSetpoint(ShooterConstants.SHOOTER_SPEED,
                ControlType.kMAXMotionVelocityControl);
        ShooterRightController.setSetpoint(ShooterConstants.SHOOTER_SPEED,
                ControlType.kMAXMotionVelocityControl);

    }

    public void stopShooter() {
        desiredPercent = 0.0;
        ShooterLeftMotor.set(0);
        ShooterRightMotor.set(0);
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
        double RightRPM = ShooterRightMotor.getEncoder().getVelocity();
        double LeftRPM = ShooterLeftMotor.getEncoder().getVelocity();

        Logger.recordOutput("Shooter/DesiredPercent", desiredPercent);
        // Applied voltage to shooter motor.
        Logger.recordOutput("Shooter/AppliedVolts", ShooterLeftMotor.getAppliedOutput() * ShooterLeftMotor.getBusVoltage());
        Logger.recordOutput("ShooterRightRPM", RightRPM);
        Logger.recordOutput("ShooterLeftRPM", LeftRPM);
        Logger.recordOutput("ShooterTargetRPM", ShooterConstants.SHOOTER_SPEED);


    }
}
