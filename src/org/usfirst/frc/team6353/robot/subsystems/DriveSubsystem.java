package org.usfirst.frc.team6353.robot.subsystems;


import org.usfirst.frc.team6353.robot.RobotMap;
import org.usfirst.frc.team6353.robot.commands.DriveWithJoystickCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	VictorSP driveFrontLeft;
	VictorSP driveFrontRight;
	VictorSP driveRearLeft;
	VictorSP driveRearRight;
	RobotDrive robotDrive;
	double leftSpeed = 0;
	double rightSpeed = 0;
	
	public DriveSubsystem(){
		
		System.out.println("DriveSubsystem Initializing");
		
		driveFrontLeft = new VictorSP(RobotMap.DriverFrontLeftPort);
		driveFrontRight = new VictorSP(RobotMap.DriverFrontRightPort);
		driveRearLeft = new VictorSP(RobotMap.DriverRearLeftPort);
		driveRearRight = new VictorSP(RobotMap.DriverRearRightPort);
		
		robotDrive = new RobotDrive(driveFrontLeft, driveRearLeft, driveFrontRight, driveRearRight);
		
		
	}
    public void initDefaultCommand() {
    	//System.out.println("SetDefaultCommand");
    	setDefaultCommand(new DriveWithJoystickCommand());
    	//System.out.println("Set complete");
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void stop(){
    	robotDrive.tankDrive(0, 0);
    }
    
    
    
    public void tankDrive(Joystick joy) {
    	double x = joy.getRawAxis(RobotMap.DriverHorizontalAxisPort);
    	double y = joy.getRawAxis(RobotMap.DriverVerticalAxisPort);
    	double DistanceFromZero = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    	double angle = Math.acos(Math.abs(x) / DistanceFromZero);
    	double Proportiontozero = (angle > Math.PI / 4 ? Math.abs(y) : Math.abs(x));
    	double anglespeed = angle / (Math.PI / 2);
    	double controlspeedconstant = - joy.getRawAxis(RobotMap.DriverSpeedControlAxisPort) / 2 + 1.0 / 2;
    	
    	
    	if(y < 0) {
    		if(x == 0) {
    			leftSpeed = 1;
    			rightSpeed = 1;
    		}
    		if(x > 0) {
    			leftSpeed = 1;
    			rightSpeed = anglespeed;
    		}
    		else {
    			leftSpeed = anglespeed;
    			rightSpeed = 1;
    		}
    	}
    	else if(x!=0 && angle <= 0.1 * Math.PI) {
    		if(x > 0) {
    			leftSpeed = 1;
    			rightSpeed = 0;
    		}
    		else if(x < 0) {
    			leftSpeed = 0;
    			rightSpeed = 1;
    		}
    			
    	}
    	else if(y > 0.8) {
    		leftSpeed = -1;
    		rightSpeed = -1;
    	}
    	
    	else {
    		leftSpeed = 0;
    		rightSpeed = 0;
    	}
    	
    	//System.out.println("LSpeed: " + leftspeed);
    	//System.out.println("RSpeed: " + rightspeed);
    	//System.out.println("Speed Control Constant: " + controlspeedconstant);
    	//System.out.println(controlspeedconstant + " " + Proportiontozero + " " + leftspeed);
    	//System.out.println(controlspeedconstant + " " + Proportiontozero + " " +  rightspeed);
    	tankDrive(controlspeedconstant * Proportiontozero * leftSpeed,
    			controlspeedconstant * Proportiontozero * rightSpeed);
    	
    			
    }
    
    public void tankDrive(double leftValue, double rightValue) {
    	leftSpeed = leftValue;
    	rightSpeed = rightValue;
		robotDrive.tankDrive(RobotMap.DriverSpeedControlConstant * leftValue,
				RobotMap.DriverSpeedControlConstant * rightValue);
	}

	public void tankDrive(double leftValue, double rightValue, boolean isSquareInput) {
		leftSpeed = leftValue;
    	rightSpeed = rightValue;
		robotDrive.tankDrive(leftValue, rightValue, isSquareInput);
	}
	public void arcadeDrive(double speed, double rotateValue) {
		robotDrive.arcadeDrive(speed, rotateValue);	
	}
	public double getLeftSpeed() {
		return leftSpeed;
	}
	public double getRightSpeed() {
		return rightSpeed;
	}
	
	
}

