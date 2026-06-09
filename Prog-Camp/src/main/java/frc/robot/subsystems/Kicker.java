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

import frc.robot.Constants.KickerConstants;
import frc.robot.Configs;
import org.littletonrobotics.junction.Logger;

public class Kicker extends SubsystemBase {
    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex KickerLeftMotor = new SparkFlex(KickerConstants.KICKER_LEFT_ID, MotorType.kBrushless);
    private SparkClosedLoopController KickerLeftController = KickerLeftMotor.getClosedLoopController();

    private SparkFlex KickerRightMotor = new SparkFlex(KickerConstants.KICKER_RIGHT_ID, MotorType.kBrushless);
    private SparkClosedLoopController KickerRightController = KickerRightMotor.getClosedLoopController();


    public Kicker() {
        KickerLeftMotor.configure(Configs.KickerSubsystem.KickerMotorLeftConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        KickerRightMotor.configure(Configs.KickerSubsystem.KickerMotorRightConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT KICKER MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void runOuttake() {
        KickerLeftController.setSetpoint(KickerConstants.KICKER_RPM,
                ControlType.kMAXMotionVelocityControl);
        KickerRightController.setSetpoint(KickerConstants.KICKER_RPM,
                ControlType.kMAXMotionVelocityControl);

    }

    public void runKicker() {
        KickerLeftController.setSetpoint(KickerConstants.KICKER_RPM,
                ControlType.kMAXMotionVelocityControl);
        KickerRightController.setSetpoint(KickerConstants.KICKER_RPM,
                ControlType.kMAXMotionVelocityControl);

    }

    public void stopKicker() {
        desiredPercent = 0.0;
        KickerLeftMotor.set(0);
        KickerRightMotor.set(0);
    }

    public Command runKickerCommand() {
        return new RunCommand(() -> runKicker(), this)
                .finallyDo(interrupted -> stopKicker());
    }

    public Command runOuttakeCommand() {
        return new RunCommand(() -> runOuttake(), this)
                .finallyDo(interrupted -> stopKicker());
    }

    public Command stopKickerCommand() {
        return new RunCommand(() -> stopKicker(), this);
    }

    public Command runDefaultCommand()
    {
        return stopKickerCommand();
    }

    @Override
    public void periodic() {
        // AdvantageKit Logging
        // Commanded kicker motor percent output.
        double RightRPM = KickerRightMotor.getEncoder().getVelocity();
        double LeftRPM = KickerLeftMotor.getEncoder().getVelocity();

        Logger.recordOutput("Kicker/DesiredPercent", desiredPercent);
        // Applied voltage to kicker motor.
        Logger.recordOutput("Kicker/AppliedVolts", KickerLeftMotor.getAppliedOutput() * KickerLeftMotor.getBusVoltage());
        Logger.recordOutput("KickerRightRPM", RightRPM);
        Logger.recordOutput("KickerLeftRPM", LeftRPM);
        Logger.recordOutput("KickerTargetRPM", KickerConstants.KICKER_RPM);

    }
}