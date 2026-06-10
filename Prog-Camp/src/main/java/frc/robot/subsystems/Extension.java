package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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

import frc.robot.Constants.ExtensionConstants;
import frc.robot.Configs;
import org.littletonrobotics.junction.Logger;

public class Extension extends SubsystemBase {

    // AdvantageKit logging
    private double desiredPercent = 0.0;

    private SparkFlex ExtensionMotor = new SparkFlex(ExtensionConstants.EXTENSION_ID, MotorType.kBrushless);
    private SparkClosedLoopController ExtensionController = ExtensionMotor.getClosedLoopController();

  

    public Extension() {
        ExtensionMotor.configure(Configs.ExtensionSubsystem.ExtensionMotorConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        // THE RIGHT EXTENSION MOTOR IS FOLLOWING THE LEFT ONE!!!
    }

    public void runExtension() {
        ExtensionController.setSetpoint(ExtensionConstants.EXTEND_POS,
                ControlType.kMAXMotionPositionControl);
    }

    public void runExtensionReverse() {
        ExtensionController.setSetpoint(ExtensionConstants.RETRACT_POS,
                ControlType.kMAXMotionPositionControl);
    }

    public void stopExtension() {
        desiredPercent = 0.0;
        ExtensionMotor.set(0);
    }

    
     public Command AgitateCommand() {
        final double[] pullPositions = { 12.5, 10, 7, 5, 3 }; // each time it pushes less far in
        final double[] pushPositions = { 15, 13.5, 10, 8.5, 6 }; // each time it pulls further out
        final double finalPos = 4; // pull to this position and idle there after agitation done
        final double waitTime = ExtensionConstants.PUSHOUT_AGITATE_WAIT;
        final double waitBetween = ExtensionConstants.PUSHOUT_BETWEEN;
        Command agitate = Commands.sequence(
            Commands.run(() ->
                {// push to 11 & pull to 8
                for (int i = 0; i < 5; i++) {
                runOnce(() -> ExtensionController.setSetpoint(pullPositions[i], ControlType.kMAXMotionPositionControl));
                Commands.waitSeconds(waitTime);
                runOnce(() -> ExtensionController.setSetpoint(pushPositions[i], ControlType.kMAXMotionPositionControl));
                Commands.waitSeconds(waitTime);

                Commands.waitSeconds(waitBetween);
                }

                // end pos
                runOnce(() -> ExtensionController.setSetpoint(finalPos, ControlType.kMAXMotionPositionControl));
                Commands.idle(this);})

        )
        .finallyDo(interrupted -> runExtension());
        agitate.addRequirements(this);
        return agitate;
    }


    public Command runExtensionCommand() {
        return new RunCommand(() -> runExtension(), this)
                .finallyDo(interrupted -> stopExtension());
    }

    public Command runExtensionReverseCommand() {
        return new RunCommand(() -> runExtensionReverse(), this)
                .finallyDo(interrupted -> stopExtension());
    }

    public Command stopExtensionCommand() {
        return new RunCommand(() -> stopExtension(), this);
    }

    public Command runDefaultCommand()
    {
        return stopExtensionCommand();
    }

    @Override
    public void periodic() {
        // AdvantageKit Logging
        // Commanded kicker motor percent output.
        double ExtensionPOS = ExtensionMotor.getEncoder().getPosition();

        Logger.recordOutput("Extension/DesiredPercent", desiredPercent);
        // Applied voltage to kicker motor.
        Logger.recordOutput("Extension/AppliedVolts", ExtensionMotor.getAppliedOutput() * ExtensionMotor.getBusVoltage());
        Logger.recordOutput("ExtensionRightPOS", ExtensionPOS);
        Logger.recordOutput("ExtensionTargetPOS", ExtensionConstants.EXTEND_POS);


    }
}