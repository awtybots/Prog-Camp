package frc.robot;

import com.revrobotics.spark.config.SparkFlexConfig;

import com.revrobotics.spark.config.SparkBaseConfig.*;

import frc.robot.Constants.IntakeConstants;

import frc.robot.Constants.HopperConstants;

import frc.robot.Constants.ShooterConstants;

// import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;

public final class Configs 
{

        public static final class IntakeSubsystem {
                
            public static final SparkFlexConfig IntakeMotorLeftConfig = new SparkFlexConfig();
            public static final SparkFlexConfig IntakeMotorRightConfig = new SparkFlexConfig();
            // public static final SparkFlexConfig IntakeRightMotorConfig = new SparkFlexConfig();

                static {

                        IntakeMotorLeftConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40).voltageCompensation(12);
                        IntakeMotorRightConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40).voltageCompensation(12).inverted(true);



                        IntakeMotorLeftConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                            // Set PID values for position control. We don't need to pass a closed
                            // loop slot, as it will default to slot 0.
                            .p(IntakeConstants.p)
                            .i(IntakeConstants.i)
                            .d(IntakeConstants.d)
                            .outputRange(-1, 1)
                            .feedForward
                            .kS(IntakeConstants.s)
                            .kV(IntakeConstants.v)
                            .kA(IntakeConstants.a)
                            ;

                        IntakeMotorLeftConfig.closedLoop
                        .maxMotion.maxAcceleration(1000000);


                        IntakeMotorRightConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                            // Set PID values for position control. We don't need to pass a closed
                            // loop slot, as it will default to slot 0.
                            .p(IntakeConstants.p)
                            .i(IntakeConstants.i)
                            .d(IntakeConstants.d)
                            .outputRange(-1, 1)
                            .feedForward
                            .kS(IntakeConstants.s)
                            .kV(IntakeConstants.v)
                            .kA(IntakeConstants.a)
                            ;

                        IntakeMotorRightConfig.closedLoop
                        .maxMotion.maxAcceleration(1000000);

                }

        };


        public static final class HopperSubsystem {
                
            public static final SparkFlexConfig HopperMotorLeftConfig = new SparkFlexConfig();
            public static final SparkFlexConfig HopperMotorRightConfig = new SparkFlexConfig();
            // public static final SparkFlexConfig IntakeRightMotorConfig = new SparkFlexConfig();

                static {

                        HopperMotorLeftConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40).voltageCompensation(12);
                        HopperMotorRightConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40).voltageCompensation(12).follow(HopperConstants.HOPPER_LEFT_ID, true);



                        HopperMotorLeftConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                            // Set PID values for position control. We don't need to pass a closed
                            // loop slot, as it will default to slot 0.
                            .p(HopperConstants.p)
                            .i(HopperConstants.i)
                            .d(HopperConstants.d)
                            .outputRange(-1, 1)
                            .feedForward
                            .kS(HopperConstants.s)
                            .kV(HopperConstants.v)
                            .kA(HopperConstants.a)
                            ;

                        HopperMotorLeftConfig.closedLoop
                        .maxMotion.maxAcceleration(1000000);


                        HopperMotorRightConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                            // Set PID values for position control. We don't need to pass a closed
                            // loop slot, as it will default to slot 0.
                            .p(HopperConstants.p)
                            .i(HopperConstants.i)
                            .d(HopperConstants.d)
                            .outputRange(-1, 1)
                            .feedForward
                            .kS(HopperConstants.s)
                            .kV(HopperConstants.v)
                            .kA(HopperConstants.a)
                            ;

                        HopperMotorRightConfig.closedLoop
                        .maxMotion.maxAcceleration(1000000);

                }

        };


        public static final class ShooterSubsystem {
                
            public static final SparkFlexConfig ShooterMotorLeftConfig1 = new SparkFlexConfig();
            public static final SparkFlexConfig ShooterMotorRightConfig1 = new SparkFlexConfig();
            public static final SparkFlexConfig ShooterMotorLeftConfig2 = new SparkFlexConfig();
            public static final SparkFlexConfig ShooterMotorRightConfig2 = new SparkFlexConfig();
            // public static final SparkFlexConfig ShooterRightMotorConfig = new SparkFlexConfig();

                static {

                        ShooterMotorLeftConfig1.idleMode(IdleMode.kCoast).smartCurrentLimit(40).voltageCompensation(12);
                        ShooterMotorRightConfig1.idleMode(IdleMode.kCoast).smartCurrentLimit(40).voltageCompensation(12).follow(ShooterConstants.SHOOTER_L1_ID,true);
                        ShooterMotorLeftConfig2.idleMode(IdleMode.kCoast).smartCurrentLimit(40).voltageCompensation(12).follow(ShooterConstants.SHOOTER_L2_ID,false);
                        ShooterMotorRightConfig2.idleMode(IdleMode.kCoast).smartCurrentLimit(40).voltageCompensation(12).follow(ShooterConstants.SHOOTER_R1_ID,false);



                        ShooterMotorLeftConfig1.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                            // Set PID values for position control. We don't need to pass a closed
                            // loop slot, as it will default to slot 0.
                            .p(ShooterConstants.p)
                            .i(ShooterConstants.i)
                            .d(ShooterConstants.d)
                            .outputRange(-1, 1)
                            .feedForward
                            .kS(ShooterConstants.s)
                            .kV(ShooterConstants.v)
                            .kA(ShooterConstants.a)
                            ;

                        ShooterMotorLeftConfig1.closedLoop
                        .maxMotion.maxAcceleration(1000000);


                        // ShooterMotorRightConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                        //     // Set PID values for position control. We don't need to pass a closed
                        //     // loop slot, as it will default to slot 0.
                        //     .p(ShooterConstants.p)
                        //     .i(ShooterConstants.i)
                        //     .d(ShooterConstants.d)
                        //     .outputRange(-1, 1)
                        //     .feedForward
                        //     .kS(ShooterConstants.s)
                        //     .kV(ShooterConstants.v)
                        //     .kA(ShooterConstants.a)
                        //     ;

                        // ShooterMotorRightConfig.closedLoop
                        // .maxMotion.maxAcceleration(1000000);

                }

        };


}
