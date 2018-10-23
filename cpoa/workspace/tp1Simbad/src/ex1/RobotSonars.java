package ex1;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

public class RobotSonars extends Agent
{
	RangeSensorBelt sonars;

	public RobotSonars(Vector3d pos, String name)
	{
		super(pos, name);
		sonars = RobotFactory.addSonarBeltSensor(this,8);
	}

	@Override
	protected void performBehavior()
	{
		setTranslationalVelocity(0.9);
		if ((getCounter() % 100)==0)
			setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
		
		//toutes les 20 frames
		if (getCounter()%20==0)
		{// afficher les mesures de chaque sonar
			for (int i=0;i< sonars.getNumSensors();i++)
			{
				double range = sonars.getMeasurement(i);
				double angle = sonars.getSensorAngle(i);
				boolean hit = sonars.hasHit(i);
				
				if(hit) System.out.println("le " +i+ " a tapé !");
				System.out.println("Sonar at angle "+ angle + "measured range ="+range+ " has hit something:"+hit);
				
				if(angle > 2) setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
			}
		}
	}
	
	
}
