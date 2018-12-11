package tp3;

import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CherryAgent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;
import simbad.sim.SimpleAgent;
import simbad.sim.Simulator;

public class MyRobot extends Agent
{
	private RangeSensorBelt sonars;
	
	private static int nbInst = 0;
	private int numRbt;
	
	/**
	 * Attribut de classe définissant la vitesse par défaut de ce type de robot soit 0.5
	 * */
	public static double DEFAULT_SPEED = 0.5;
	
	/**
	 * Vitesse actuelle du robot
	 * */
	private double vitesse;
	
	/**
	 * Simulator sur lequel peut agir le robot
	 * */
	private Simulator simulator;
	
	private MyEnv env;
	
	public MyRobot(Vector3d pos, String name, Simulator simulator, MyEnv env)
	{
		super(pos, name);
		
		this.simulator = simulator;
		this.env = env;
		
		this.setCanBeTraversed(false);
		
		this.sonars = RobotFactory.addSonarBeltSensor(this,8);
		this.numRbt = ++nbInst;
		
		this.vitesse = MyRobot.DEFAULT_SPEED; // 0.5 m/s par default
	}
	
	@Override
	protected void performBehavior()
	{
		super.performBehavior();
		
		// avance à 0.5 m/s de base en continu, vitesse peut être changée via modificateurs
		setTranslationalVelocity(this.vitesse);
		
		if (anOtherAgentIsVeryNear())
		{
			SimpleAgent agent = getVeryNearAgent();
			
			if (agent instanceof CherryAgent){
				agent.detach();
				env.attraperCerise();
			}
			
			if(agent instanceof RobotEnnemi)
				env.stopSimulation("Perdu!\nVous avez été touché par un robot ennemi");
		}
		
		
	}
	
	/**
	 * Incrémente la vitesse de +0.5 m/s (tp4)
	 * */
	public void incSpeed(){
		this.vitesse += 0.5;
	}
	
	/**
	 * Diminue la vitesse de -0.5 m/s (tp4)
	 * */
	public void decSpeed(){
		if(this.vitesse - DEFAULT_SPEED >= DEFAULT_SPEED) this.vitesse -= 0.5;
	}
	
	/**
	 * Remet la vitesse par défaut de 0.5 m/s (tp4)
	 * */
	public void resetSpeed(){
		this.vitesse = DEFAULT_SPEED;
	}
	
	/**
	 * Permet d'obtenir la vitesse actuelle du robot
	 * @return vitesse en double
	 * */
	public double getVitesse(){
		return vitesse;
	}

}
