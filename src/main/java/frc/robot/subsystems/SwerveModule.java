package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

public class SwerveModule extends SubsystemBase {

    private final WPI_TalonFX driveMotor;
    private final WPI_TalonFX turningMotor;

    private final CANCoder turningEncoder;

    private final PIDController turningPidController;

    private final double absoluteEncoderOffsetRad;
    private final double absoluteDriveEncoderOffset;

    public SwerveModule(int driveMotorId, int turningMotorId, int CANCoderId, boolean driveMotorReversed, boolean turningMotorReversed, double absoluteEncoderOffset) {

        this.absoluteEncoderOffsetRad = absoluteEncoderOffset;

        driveMotor = new WPI_TalonFX(driveMotorId);
        turningMotor = new WPI_TalonFX(turningMotorId);

        driveMotor.setInverted(driveMotorReversed);
        turningMotor.setInverted(turningMotorReversed);
       
        turningEncoder = new CANCoder(CANCoderId);

        CANCoderConfiguration config = new CANCoderConfiguration();
        // set units of the CANCoder to radians, with velocity being radians per second
        config.sensorCoefficient = 2 * Math.PI / 4096.0;
        config.unitString = "rad";
        turningEncoder.configAllSettings(config);

        turningPidController = new PIDController(ModuleConstants.kPTurning, 0, 0);
        turningPidController.enableContinuousInput(-Math.PI, Math.PI);

        absoluteDriveEncoderOffset = driveMotor.getSelectedSensorPosition();
    }

    public double getTurningPosition() {
        return turningEncoder.getAbsolutePosition() - absoluteEncoderOffsetRad;
    }
    
    public double getTurningVelocity() {
        return turningEncoder.getVelocity();
    }

    public SwerveModuleState getState() {
        // * 10d because getSelectedSensorVelocity() returns ticks/100ms; 
        return new SwerveModuleState(driveMotor.getSelectedSensorVelocity() * 10d * Constants.ModuleConstants.kDriveEncoderTicks2Meter, new Rotation2d(getTurningPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop();
            return;
        }
        state = SwerveModuleState.optimize(state, getState().angle);
        driveMotor.set(state.speedMetersPerSecond / DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        turningMotor.set(turningPidController.calculate(getTurningPosition(), state.angle.getRadians()));
    }

    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition((driveMotor.getSelectedSensorPosition()-absoluteDriveEncoderOffset) * Constants.ModuleConstants.kDriveEncoderTicks2Meter, getState().angle);
    }

    public void stop() {
        driveMotor.set(0);
        turningMotor.set(0);
    }

    //private int count = 0;

    @Override
    public void periodic() {
    /*
        count++;
        if (count % 150 == 0) {
            System.out.println("actual m/s: " + getState().speedMetersPerSecond + "\n.\texpected m/s: " + DEBUG_lastms);
            System.out.println("power: " + DEBUG_lastms / DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
            //System.out.println("distance m: " + getModulePosition().distanceMeters);
            count = 0;
        }
    */
    }

    public double getPosition(){
        return driveMotor.getSelectedSensorPosition();
    }

    public void setMotor(double speed){
        driveMotor.set(speed);
    }
}

