package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import frc.robot.Constants.DriveConstants;;
public class SwervePID extends CommandBase{
    PIDController pid;
    SwerveModule modulefl;
    SwerveModule modulefr;
    SwerveModule modulebl;
    SwerveModule modulebr;

    public SwervePID(){
        modulefl = new SwerveModule(
            DriveConstants.kFrontLeftDriveMotorPort,
            DriveConstants.kFrontLeftTurningMotorPort,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
            DriveConstants.kFrontLeftDriveEncoderReversed,
            DriveConstants.kFrontLeftTurningEncoderReversed,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad
            );    
        modulefr = new SwerveModule(
            DriveConstants.kFrontRightDriveMotorPort,
            DriveConstants.kFrontRightTurningMotorPort,
            DriveConstants.kFrontRightDriveAbsoluteEncoderPort,
            DriveConstants.kFrontRightDriveEncoderReversed,
            DriveConstants.kFrontRightTurningEncoderReversed,
            DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad);
        modulebl = new SwerveModule(
            DriveConstants.kBackLeftDriveMotorPort,
            DriveConstants.kBackLeftTurningMotorPort,
            DriveConstants.kBackLeftDriveAbsoluteEncoderPort,
            DriveConstants.kBackLeftDriveEncoderReversed,
            DriveConstants.kBackLeftTurningEncoderReversed,
            DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad
        );
        modulebr = new SwerveModule(
            DriveConstants.kBackRightDriveMotorPort,
            DriveConstants.kBackRightTurningMotorPort,
            DriveConstants.kBackRightDriveAbsoluteEncoderPort,
            DriveConstants.kBackRightDriveEncoderReversed,
            DriveConstants.kBackRightTurningEncoderReversed,
            DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad
        );
        //fl: 0.000053, 0.000069, 0
        //fr: 0.000063, 0.000069,0
        //bl: 0.000053, 0.000069, 0
        //br: 0.000073, 0.000069,0
        pid = new PIDController(0.000073,0.000069,0);
        pid.setTolerance(0.5);

    }

    @Override
    public void initialize(){
        //pid.setSetpoint(modulefl.getPosition() + 1*2048*6.75);
        //pid.setSetpoint(modulefr.getPosition()+ 1*2048*6.75);
        // pid.setSetpoint(modulebl.getPosition()+ 1*2048*6.75);
        pid.setSetpoint(modulebr.getPosition()+ 1*2048*6.75);

    }


    @Override
    public void execute(){
        //double speed = pid.calculate(module.getPosition());
        //module.setMotor(speed);
        //System.out.println(speed);
        double speed1 = pid.calculate(modulebr.getPosition());
        modulebr.setMotor(speed1);
        System.out.println(speed1);
    }

    @Override
    public void end(boolean interupted){
        
    }
    
}
