package simbad.gui;

import tp3.MyRobot;
import simbad.sim.World;

public class RobotFollower extends AgentFollower
{
	MyRobot robot;
	public RobotFollower(World world, MyRobot robot)
	{
		super(world, robot);
		this.robot = robot;
	}
}
