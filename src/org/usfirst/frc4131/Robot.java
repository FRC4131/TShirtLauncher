/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.							 */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.															   */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4131;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;

public class Robot extends SimpleRobot{
	private Joystick joystick = new Joystick(1);
	private RobotDrive drive = new RobotDrive(1, 2, 3, 4);
	public void operatorControl(){
		while(isEnabled() && isOperatorControl()){
			drive.arcadeDrive(joystick.getY(), joystick.getX());
		}
	}
}
