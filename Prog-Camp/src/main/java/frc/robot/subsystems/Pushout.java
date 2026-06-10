package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.PushoutConstants;


// import com.revrobotics.spark.ClosedLoopSlot;
// import com.revrobotics.spark.SparkBase.ControlType;
// import com.revrobotics.REVLibError;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;

public class Pushout extends SubsystemBase {

    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex PushoutMotor = new SparkFlex(PushoutConstants.PUSHOUT_ID, MotorType.kBrushless);
    private SparkClosedLoopController PushoutController = PushoutMotor.getClosedLoopController();

    

    public Pushout() {
        PushoutMotor.configure(Configs.PushoutSubsystem.PushoutMotorConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    public void extendPushout() {
        PushoutController.setSetpoint(PushoutConstants.PUSHOUT_EXTENDED_POSITION,
                ControlType.kMAXMotionPositionControl);

    }

    public void retractPushout() {
        PushoutController.setSetpoint(PushoutConstants.PUSHOUT_RETRACTED_POSITION,
                ControlType.kMAXMotionPositionControl);

    }

    public void stopPushout() {
        desiredPercent = 0.0;
        PushoutMotor.set(0);
    }


    public void agitate() {
        for(int i=0; i<PushoutConstants.PUSHOUT_PUSH_POSITIONS.length; i++) {
            PushoutController.setSetpoint(PushoutConstants.PUSHOUT_PUSH_POSITIONS[i],
                    ControlType.kMAXMotionPositionControl);
            Commands.waitSeconds(PushoutConstants.PUSHOUT_PUSH_WAIT_TIME);
            PushoutController.setSetpoint(PushoutConstants.PUSHOUT_PULL_POSITIONS[i],
                    ControlType.kMAXMotionPositionControl);
            Commands.waitSeconds(PushoutConstants.PUSHOUT_PULL_WAIT_TIME);
        }
    }

    public Command extendPushoutCommand() {
        return this.runOnce(() -> extendPushout());
                // .finallyDo(interrupted -> stopPushout());
    }

    public Command retractPushoutCommand() {
        return this.runOnce(() -> retractPushout());
                // .finallyDo(interrupted -> stopPushout());
    }

    public Command stopPushoutCommand() {
        return new RunCommand(() -> stopPushout(), this);
    }

    public Command agitateCommand() {
        return new RunCommand(() -> agitate(), this);
    }

    public Command runDefaultCommand()
    {
        return stopPushoutCommand();
    }

    // @Override
    // public void periodic() {
    //     // AdvantageKit Logging
    //     // Commanded Pushout motor percent output.
    //     double RightRPM = PushoutMotor.getEncoder().getVelocity();

    //     // Logger.recordOutput("Pushout/DesiredPercent", desiredPercent);
    //     // Applied voltage to Pushout motor.
    //     // Logger.recordOutput("Pushout/AppliedVolts", PushoutMotor.getAppliedOutput() * PushoutMotor.getBusVoltage());
    //     // Logger.recordOutput("PushoutRPM", RightRPM);
    //     // Logger.recordOutput("PushoutTargetPosition", PushoutConstants.PUSHOUT);


    // }
}