/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.							 */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.															   */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4131;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class TShirtLauncher extends SimpleRobot{
	private Joystick controller = new Joystick(1);
	private JoystickButton buttonPrev = new JoystickButton(controller, 5);//Left bumper
	private JoystickButton buttonNext = new JoystickButton(controller, 6);//Right bumper
	private boolean pressedPrev = false, pressedNext = false;
	private int fireTicks;//The number of ticks the fire button has been down. When this reaches 600 (3s), it fires.
	private RobotDrive drive = new RobotDrive(1, 2, 3, 4);
	private Solenoid[] launcher = new Solenoid[6];
	private int current = 0;
//	private Jaguar elevator = new Jaguar(5);
	private Relay lights = new Relay(1);
	private Relay siren = new Relay(2);
	public void robotInit(){
		for(int i=0;i<launcher.length;i++){launcher[i] = new Solenoid(i+1); launcher[i].set(false);}
//		elevator.set(0);
		lights.set(Value.kForward);
		siren.set(Value.kOff);
	}
	public void autonomous(){}
	public void operatorControl(){
		while(isEnabled() && isOperatorControl()){
			drive.arcadeDrive(controller.getRawAxis(5), controller.getRawAxis(4));
			if(buttonPrev.get() && !pressedPrev){if(current==0) current = launcher.length-1; else current--;}
			pressedPrev = buttonPrev.get();
			if(buttonNext.get() && !pressedNext){if(current==launcher.length-1) current = 0; else current++;}
			pressedNext = buttonNext.get();
//			elevator.set(controller.getRawAxis(2));//Left stick Y-axis
			if(controller.getRawAxis(3)<=-0.9 && fireTicks>=0){//Right trigger, with 0.1 deadband
				fireTicks++;
				if(fireTicks>=600){
					launcher[current].set(true);
					Timer.delay(0.15);
					launcher[current].set(false);
					if(current==launcher.length-1) current = 0; else current++;
					fireTicks=-1;//Disable firing until trigger is released
				}
			}
			if(fireTicks>0) siren.set(Value.kForward); else siren.set(Value.kOff);
			if(Math.abs(controller.getRawAxis(3))<0.1) fireTicks=0;
			Timer.delay(0.005);
		}
	}
	public void test(){}
}
