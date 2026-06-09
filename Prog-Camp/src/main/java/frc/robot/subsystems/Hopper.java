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
// import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;

import frc.robot.Constants.HopperConstants;
import frc.robot.Configs;
import org.littletonrobotics.junction.Logger;

public class Hopper extends SubsystemBase {

    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex HopperLeftMotor = new SparkFlex(HopperConstants.HOPPER_LEFT_ID, MotorType.kBrushless);
    private SparkClosedLoopController HopperLeftController = HopperLeftMotor.getClosedLoopController();

    private SparkFlex HopperRightMotor = new SparkFlex(HopperConstants.HOPPER_RIGHT_ID, MotorType.kBrushless);
    // private SparkClosedLoopController HopperRightController = HopperRightMotor.getClosedLoopController();
  

    public Hopper() {
        HopperLeftMotor.configure(Configs.HopperSubsystem.HopperMotorLeftConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        HopperRightMotor.configure(Configs.HopperSubsystem.HopperMotorRightConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT INTAKE MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void runHopper() {
        HopperLeftController.setSetpoint(HopperConstants.HOPPER_RPM,
                ControlType.kMAXMotionVelocityControl);
        // IntakeRightController.setSetpoint(IntakeConstants.INTAKE_RPM,
        //         ControlType.kMAXMotionVelocityControl);

    }

    public void runHopperReverse() {
        HopperLeftController.setSetpoint(HopperConstants.HOPPER_REVERSE_RPM,
                ControlType.kMAXMotionVelocityControl);
        // HopperRightController.setSetpoint(IntakeConstants.OUTTAKE_RPM,
                // ControlType.kMAXMotionVelocityControl);

    }

    public void stopHopper() {
        desiredPercent = 0.0;
        HopperLeftMotor.set(0);
        HopperRightMotor.set(0);
    }

    public Command runHopperCommand() {
        return new RunCommand(() -> runHopper(), this)
                .finallyDo(interrupted -> stopHopper());
    }

    public Command runHopperReverseCommand() {
        return new RunCommand(() -> runHopperReverse(), this)
                .finallyDo(interrupted -> stopHopper());
    }

    public Command stopHopperCommand() {
        return new RunCommand(() -> stopHopper(), this);
    }

    public Command runDefaultCommand()
    {
        return stopHopperCommand();
    }

    @Override
    public void periodic() {
        // AdvantageKit Logging
        // Commanded Hopper motor percent output.
        double RightRPM = HopperRightMotor.getEncoder().getVelocity();
        double LeftRPM = HopperLeftMotor.getEncoder().getVelocity();

        Logger.recordOutput("Hopper/DesiredPercent", desiredPercent);
        // Applied voltage to Hopper motor.
        Logger.recordOutput("Hopper/AppliedVolts", HopperLeftMotor.getAppliedOutput() * HopperLeftMotor.getBusVoltage());
        Logger.recordOutput("HopperRightRPM", RightRPM);
        Logger.recordOutput("HopperLeftRPM", LeftRPM);
        Logger.recordOutput("HopperTargetRPM", HopperConstants.HOPPER_RPM);


    }
}