// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.PhotonInfo;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.PhotonVision;

public class RobotContainer {
  PhotonVision pv;
 
  public RobotContainer() {
    pv = new PhotonVision();
    //pv.setDefaultCommand(new DistanceFromTag());
    configureBindings();
  }

  private void configureBindings(){
     
  } 
  private void updateShuffleboard(){
    SmartDashboard.putNumber("Area",pv.getArea(null) );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }


}
