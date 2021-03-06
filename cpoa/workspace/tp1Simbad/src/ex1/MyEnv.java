package ex1;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Arch;
import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;

public class MyEnv extends EnvironmentDescription
{

	public MyEnv()
	{
		add(new Arch(new Vector3d(3,0,-3),this));
		add(new Box(new Vector3d(5,0,5), new Vector3f(6,90,6), this));
		//add(new Box(new Vector3d(4,0,4), new Vector3f(1,90,1), this));
		add(new MyRobot(new Vector3d(0, 0, 0),"mon robot 1"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
		
	}

}
