/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2073.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	TalonSRX talon = new TalonSRX(4);
	double p = .05;
	double i = 0;
	double d = 0;
	boolean started = false;
	private PIDFUtil pidController = new PIDFUtil(p, i, d, 0, talon, 10 /* Interval in milliseconds */,
			1 /* max output */);

	@Override
	public void teleopInit() {
		talon.setSensorPhase(true);
	}

	@Override
	public void robotPeriodic() {
		if (isEnabled()) {
			if (!started) {
				talon.setSelectedSensorPosition(0, 0, 0);
				pidController.startPID(4096);
				started = true;
			}
			System.out.println(talon.getSelectedSensorPosition(0));
			talon.set(ControlMode.PercentOutput, pidController.getOutput());
		}
	}

	@Override
	public void robotInit() {
		SmartDashboard.putNumber("P", .1);
		SmartDashboard.putNumber("I", 0);
		SmartDashboard.putNumber("D", .3);
	}

	@Override
	public void disabledInit() {
		started = false;
		p = SmartDashboard.getNumber("P", .1);
		i = SmartDashboard.getNumber("I", 0);
		d = SmartDashboard.getNumber("D", .3);
		talon.setSelectedSensorPosition(0, 0, 0);
		pidController = new PIDFUtil(p, i, d, 0, talon, 10, 1);
	}

}
