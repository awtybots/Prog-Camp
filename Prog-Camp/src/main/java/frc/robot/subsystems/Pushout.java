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

import frc.robot.Constants.PushoutConstants;
import frc.robot.Configs;
import org.littletonrobotics.junction.Logger;

public class Pushout extends SubsystemBase {

    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex PushoutLeftMotor = new SparkFlex(PushoutConstants.PUSHOUT_LEFT_ID, MotorType.kBrushless);
    private SparkClosedLoopController PushoutLeftController = PushoutLeftMotor.getClosedLoopController();

    private SparkFlex PushoutRightMotor = new SparkFlex(PushoutConstants.PUSHOUT_RIGHT_ID, MotorType.kBrushless);
    private SparkClosedLoopController PushoutRightController = PushoutRightMotor.getClosedLoopController();
  

    public Pushout() {
        PushoutLeftMotor.configure(Configs.PushoutSubsystem.PushoutMotorLeftConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        PushoutRightMotor.configure(Configs.PushoutSubsystem.PushoutMotorRightConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT INTAKE MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void runPushout() {
        PushoutLeftController.setSetpoint(PushoutConstants.PUSHOUT_extended_position,
                ControlType.kMAXMotionPositionControl);
        PushoutRightController.setSetpoint(PushoutConstants.PUSHOUT_extended_position,
                ControlType.kMAXMotionPositionControl);

    }  public void runPushout_retracted_position() {
        PushoutLeftController.setSetpoint(PushoutConstants.PUSHOUT_retracted_position,
                ControlType.kMAXMotionPositionControl);
        PushoutRightController.setSetpoint(PushoutConstants.PUSHOUT_retracted_position,
                ControlType.kMAXMotionPositionControl);

    }

    public void stopPushout() {
        desiredPercent = 0.0;
        PushoutLeftMotor.set(0);
        PushoutRightMotor.set(0);
    }

    public Command runPushoutCommand() {
        return new RunCommand(() -> runPushout(), this)
                .finallyDo(interrupted -> stopPushout());
    }

    public Command runPushout_retracted_positionCommand() {
        return new RunCommand(() -> runPushout_retracted_position(), this)
                .finallyDo(interrupted -> stopPushout());
    }

    public Command stopPushoutCommand() {
        return new RunCommand(() -> stopPushout(), this);
    }

    public Command runDefaultCommand()
    {
        return stopPushoutCommand();
    }

    @Override
    public void periodic() {
        // AdvantageKit Logging
        // Commanded intake motor percent output.
        double RightRPM = PushoutRightMotor.getEncoder().getVelocity();
        double LeftRPM = PushoutLeftMotor.getEncoder().getVelocity();

        Logger.recordOutput("Pushout/DesiredPercent", desiredPercent);
        // Applied voltage to intake motor.
        Logger.recordOutput("Pushout/AppliedVolts", PushoutLeftMotor.getAppliedOutput() * PushoutLeftMotor.getBusVoltage());
        Logger.recordOutput("PushoutRightRPM", RightRPM);
        Logger.recordOutput("PushoutLeftRPM", LeftRPM);


    }
}