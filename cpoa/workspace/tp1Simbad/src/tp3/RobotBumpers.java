package tp3;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CameraSensor;
import simbad.sim.RobotFactory;

public class RobotBumpers extends Agent
{
	CameraSensor camera;
	
	public RobotBumpers(Vector3d pos, String name)
	{
		super(pos, name);
		camera = RobotFactory.addCameraSensor(this);
	}
	
	@Override
	protected void performBehavior()
	{
		setTranslationalVelocity(0.5);
		if ((getCounter() % 100)==0)
			setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
		
		
	}

}
