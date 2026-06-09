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

import frc.robot.Constants.HopperConstants;
import frc.robot.Configs;
import org.littletonrobotics.junction.Logger;

public class Hopper extends SubsystemBase {

    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex TwindexerLeftMotor = new SparkFlex(HopperConstants.TWINDEXER_LEFT_ID, MotorType.kBrushless);
    private SparkClosedLoopController TwindexerLeftController = TwindexerLeftMotor.getClosedLoopController();

    private SparkFlex TwindexerRightMotor = new SparkFlex(HopperConstants.TWINDEXER_RIGHT_ID, MotorType.kBrushless);
  

    public Hopper() {
        TwindexerLeftMotor.configure(Configs.HopperSubsystem.TwindexerMotorLeftConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        TwindexerRightMotor.configure(Configs.HopperSubsystem.TwindexerMotorRightConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT TWINDEXER MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void runTwindexer() {
        TwindexerLeftController.setSetpoint(HopperConstants.PENETRATE_RPM,
                ControlType.kMAXMotionVelocityControl);
    }

    public void runTwindexerReverse() {
        TwindexerLeftController.setSetpoint(HopperConstants.PULLOUT_RPM,
                ControlType.kMAXMotionVelocityControl);
    }

    

    public void stopTwindexer() {
        desiredPercent = 0.0;
        TwindexerLeftMotor.set(0);
        TwindexerRightMotor.set(0);
    }

    public Command runTwindexerCommand() {
        return new RunCommand(() -> runTwindexer(), this)
                .finallyDo(interrupted -> stopTwindexer());
    }

    public Command runTwindexerReverseCommand() {
        return new RunCommand(() -> runTwindexerReverse(), this)
                .finallyDo(interrupted -> stopTwindexer());
    }

    public Command stopTwindexerCommand() {
        return new RunCommand(() -> stopTwindexer(), this);
    }

    public Command runDefaultCommand()
    {
        return stopTwindexerCommand();
    }

    @Override
    public void periodic() {
        // AdvantageKit Logging
        // Commanded twindexer motor percent output.
        double TwindexerRightRPM = TwindexerRightMotor.getEncoder().getVelocity();
        double TwindexerLeftRPM = TwindexerLeftMotor.getEncoder().getVelocity();

        Logger.recordOutput("Twindexer/DesiredPercent", desiredPercent);
        // Applied voltage to twindexer motor.
        Logger.recordOutput("Twindexer/AppliedVolts", TwindexerLeftMotor.getAppliedOutput() * TwindexerLeftMotor.getBusVoltage());
        Logger.recordOutput("TwindexerRightRPM", TwindexerRightRPM);
        Logger.recordOutput("TwindexerLeftRPM", TwindexerLeftRPM);
        Logger.recordOutput("TwindexerTargetRPM", HopperConstants.PENETRATE_RPM);


    }
}