package tp3;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CherryAgent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;
import simbad.sim.SimpleAgent;

public class MyRobot extends Agent
{
	private RangeSensorBelt sonars;
	
	private static int nbInst = 0;
	private int numRbt;
	
	public MyRobot(Vector3d pos, String name)
	{
		super(pos, name);
		
		this.setCanBeTraversed(false);
		
		this.sonars = RobotFactory.addSonarBeltSensor(this,8);
		this.numRbt = ++nbInst;
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
		if ((getCounter() % 100)==0) setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
		
		if (sonars.oneHasHit())
		{
			double left  = sonars.getFrontLeftQuadrantMeasurement ();
			double right = sonars.getFrontRightQuadrantMeasurement();
			double front = sonars.getFrontQuadrantMeasurement	  ();
			
				if ((front > 0.5)||(left > 0.5)||(right > 0.5))
				{
					if (left < right) setRotationalVelocity(-1);
					else 			  setRotationalVelocity( 1);
				}
		}
		if (this.collisionDetected())
		{
			System.out.println("collision du robot " +this.numRbt);
			setTranslationalVelocity(-Math.PI / 4);
			setRotationalVelocity(Math.PI / 2) ;
		}
		
		if (anOtherAgentIsVeryNear())
		{
			SimpleAgent agent = getVeryNearAgent();
			if (agent instanceof CherryAgent)
			{
				agent.detach();
				System.out.println("cerise cueillie !");
			}
		}
		
		
	}
	

}
