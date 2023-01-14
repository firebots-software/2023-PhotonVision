package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonVisionNetwork extends SubsystemBase{
    private NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private NetworkTable table = instance.getTable("photonvision");

    public boolean hasTarget(){ 
        return table.getEntry("hasTarget").getBoolean(false);
    }

}