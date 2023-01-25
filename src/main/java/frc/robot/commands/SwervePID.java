package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveModule;
import frc.robot.Constants.DriveConstants;;
public class SwervePID extends CommandBase{
    PIDController pid;
    SwerveModule module;
    public SwervePID(){
        module = new SwerveModule(
            DriveConstants.kFrontLeftDriveMotorPort,
            DriveConstants.kFrontLeftTurningMotorPort,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
            DriveConstants.kFrontLeftDriveEncoderReversed,
            DriveConstants.kFrontLeftTurningEncoderReversed,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad
            );    
        pid = new PIDController(0.0001,0,0);
        pid.setTolerance(0.5);

    }

    @Override
    public void initialize(){
        pid.setSetpoint(module.getPosition() + 5*2048);
    }


    @Override
    public void execute(){
        double speed = pid.calculate(module.getPosition());
        module.setMotor(speed);
        System.out.println(speed);

    }

    @Override
    public void end(boolean interupted){
        
    }
    
}
