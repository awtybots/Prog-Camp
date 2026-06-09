  
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

    private SparkFlex ShooterLeftMotor1 = new SparkFlex(ShooterConstants.SHOOTER_L1_ID, MotorType.kBrushless);
    private SparkClosedLoopController ShooterLeftController = ShooterLeftMotor1.getClosedLoopController();

    private SparkFlex ShooterRightMotor1 = new SparkFlex(ShooterConstants.SHOOTER_R1_ID, MotorType.kBrushless);
    // private SparkClosedLoopController ShooterRightController = ShooterRightMotor.getClosedLoopController();
  
    private SparkFlex ShooterLeftMotor2 = new SparkFlex(ShooterConstants.SHOOTER_L2_ID, MotorType.kBrushless);
    // private SparkClosedLoopController ShooterRightController = ShooterRightMotor.getClosedLoopController();

    private SparkFlex ShooterRightMotor2 = new SparkFlex(ShooterConstants.SHOOTER_R2_ID, MotorType.kBrushless);
    // private SparkClosedLoopController ShooterRightController = ShooterRightMotor.getClosedLoopController();

    public Shooter() {
        ShooterLeftMotor1.configure(Configs.ShooterSubsystem.ShooterMotorLeftConfig1, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        ShooterRightMotor1.configure(Configs.ShooterSubsystem.ShooterMotorRightConfig1, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        ShooterLeftMotor2.configure(Configs.ShooterSubsystem.ShooterMotorLeftConfig2, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        ShooterRightMotor2.configure(Configs.ShooterSubsystem.ShooterMotorRightConfig2, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT SHOOTER MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void setRPM(double rpm) {
        ShooterLeftController.setSetpoint(rpm,
                ControlType.kMAXMotionVelocityControl);
        // ShooterRightController.setSetpoint(ShooterConstants.SHOOTER_RPM,
        //         ControlType.kMAXMotionVelocityControl);

    }

    public void staticShoot() {
        ShooterLeftController.setSetpoint(ShooterConstants.staticRPM,
                ControlType.kMAXMotionVelocityControl);
        // ShooterRightController.setSetpoint(ShooterConstants.SHOOTER_RPM,
        //         ControlType.kMAXMotionVelocityControl);

    }

    public void stopShooter() {
        desiredPercent = 0.0;
        ShooterLeftMotor1.set(0);
        // ShooterRightMotor.set(0);
    }

    public Command staticShootCommand() {
        return new RunCommand(() -> staticShoot(), this)
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
        // Commanded intake motor percent output.
        // double RightRPM = ShooterRightMotor.getEncoder().getVelocity();
        double LeftRPM = ShooterLeftMotor1.getEncoder().getVelocity();

        Logger.recordOutput("Shooter/DesiredPercent", desiredPercent);
        // Applied voltage to intake motor.
        Logger.recordOutput("Shooter/AppliedVolts", ShooterLeftMotor1.getAppliedOutput() * ShooterLeftMotor1.getBusVoltage());
        // Logger.recordOutput("ShooterRightRPM", RightRPM);
        Logger.recordOutput("ShooterLeftRPM", LeftRPM);
        Logger.recordOutput("ShooterTargetRPM", ShooterConstants.staticRPM);


    }
}

