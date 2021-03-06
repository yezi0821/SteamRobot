package org.usfirst.frc.team6353.robot.commands;

import org.usfirst.frc.team6353.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EmergStopCommand extends Command {

    public EmergStopCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
//   	setTimeout(2.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Emergy Stop");
    	if(timeSinceInitialized() < 0.2) {
    		double leftSpeed = -Robot.driveSubsystem.getLeftSpeed();
    		double rightSpeed = -Robot.driveSubsystem.getRightSpeed();
    		Robot.driveSubsystem.tankDrive(leftSpeed,rightSpeed);
    	}
    	else
    		Robot.driveSubsystem.tankDrive(0,0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
