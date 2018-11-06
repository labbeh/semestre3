package tp2;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;

public class MyRobot extends Agent
{

	public MyRobot(Vector3d pos, String name)
	{
		super(pos, name);
	}

	@Override
	protected void initBehavior()
	{
		// TODO Auto-generated method stub
		//super.initBehavior();
	}

	@Override
	protected void performBehavior()
	{
		// TODO Auto-generated method stub
		//super.performBehavior();
		
		// avance à 0.5 m/s
		setTranslationalVelocity(0.9);
		
		// changer l'angle fréquemment
		if ((getCounter() % 100)==0)
		setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
	}
	
	

}
