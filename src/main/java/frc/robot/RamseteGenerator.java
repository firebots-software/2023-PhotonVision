package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.ResetOdometer;
import frc.robot.subsystems.SwerveSubsystem;

public class RamseteGenerator {
    private static SwerveSubsystem swerve = SwerveSubsystem.getInstance();

    /**
     * Generator method to create a RamseteCommand that will follow a given path.
     *
     * @param path = trajectory object describing path
     * @return RamseteCommand to drive given path
     */
    public static Command generateCommandForPath(Trajectory path) {

        PIDController leftbackController = new PIDController(0.1, 0, 0); //TODO: tune kp later
        PIDController leftfrontController = new PIDController(0.1, 0, 0); //TODO: tune kp later
        PIDController rightbackController = new PIDController(0.1, 0, 0); //Tune kp later
        PIDController rightfrontController = new PIDController(0.1, 0, 0); //Tune kp later

        RamseteCommand ramseteCommand = new RamseteCommand(
                path,
                swerve::getPose,
                Constants.Ramsete.ramseteController,
                Constants.Ramsete.feedforward,
                Constants.DriveConstants.kDriveKinematics,
                swerve::getWheelSpeeds,
                leftbackController,
                leftfrontController,
                rightbackController,
                rightfrontController,
                // RamseteCommand passes volts to the callback
                (leftVolts, rightVolts) -> {
                    drivetrain.tankDriveVolts(leftVolts, rightVolts);

                    // SmartDashboard.putNumber("motprof left target", leftController.getSetpoint());
                    // SmartDashboard.putNumber("motprof right target", rightController.getSetpoint());

                    // SmartDashboard.putNumber("motprof left speed", drivetrain.getLeftEncoderVelocityMetersPerSec());
                    // SmartDashboard.putNumber("motprof right speed", drivetrain.getRightEncoderVelocityMetersPerSec());

                    // System.out.println("executing mot prof");
                },
                swerve
        );

    
        return new ResetOdometer().andThen(ramseteCommand);
    }


}