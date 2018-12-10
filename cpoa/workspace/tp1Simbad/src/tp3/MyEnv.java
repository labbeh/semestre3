package tp3;

import java.awt.Color;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Arch;
import simbad.sim.BallAgent;
import simbad.sim.BlockWorldObject;
import simbad.sim.Box;
import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Simulator;
import simbad.sim.Wall;

public class MyEnv extends EnvironmentDescription
{
	
	private HashMap<String, BlockWorldObject> envConfig;
	
	// ne sert à rien pour le moment, fait car précisé dans la consigne
	private ArrayList<Box> 	alBox ;
	private ArrayList<Arch> alArch;
	private ArrayList<Wall> alWall;
	
	/**
	 * liste de robots (10 max)
	 **/
	private ArrayList<MyRobot> listeRobots;
	
	/**
	 * Liste des robots ennemis
	 * */
	private ArrayList<RobotEnnemi> ennemis;
		
	/**
	 * Instance de Simulator afin que les objets de l'environnement puisse intéragir avec le simulateur
	 * */
	private Simulator simulator;
	

	public MyEnv( String nomFichier )
	{	
		this.envConfig = new HashMap<>();
		
		this.alArch = new ArrayList<>();
		this.alBox  = new ArrayList<>();
		this.alWall = new ArrayList<>();
		
		this.ennemis = new ArrayList<>();
		
		InputStream ips = this.getClass().getResourceAsStream("/" +nomFichier);
		InputStreamReader ipsr = new InputStreamReader(ips);
		Scanner sc = new Scanner(ipsr);
		
		while(sc.hasNextLine())
		{
			Scanner scLigne = new Scanner(sc.nextLine());
			
			char type = scLigne.next().charAt(0);
			
			if(type == 'R' /*|| type == 'E'*/)
			{
				int nbRobots = Integer.parseInt(scLigne.next());
				
				if(type == 'R'){
					this.listeRobots = new ArrayList<>(nbRobots);
				
					for(int cpt=0; cpt<nbRobots; cpt++)
					{
						this.listeRobots.add(new MyRobot(new Vector3d((int)(Math.random()*cpt), 0, (int)(Math.random()*cpt)), "MyRobot " +cpt, simulator, this));
						add(this.listeRobots.get(cpt));
					}
				}
				
				/*if(type == 'E'){
					this.ennemis = new ArrayList<>(nbRobots);
				
					for(int cpt=0; cpt<nbRobots; cpt++)
					{
						this.ennemis.add(new RobotEnnemi(new Vector3d((int)(Math.random()*cpt), 0, (int)(Math.random()*cpt)), "Ennemi " +cpt));
						add(this.ennemis.get(cpt));
					}
					System.out.println(ennemis);
				}*/
				
				scLigne.close();
				
				//if(!sc.hasNextLine()) break;
				
				/*if(sc.hasNextLine()){
					scLigne = new Scanner(sc.nextLine());
					type = scLigne.next().charAt(0);
				}*/
			}
			
			else{
			
    			double x = Double.parseDouble(scLigne.next());
    			double y = Double.parseDouble(scLigne.next());
    			double z = Double.parseDouble(scLigne.next());
    			
    			if(type == 'W')
    			{
    				float length;
    				float height;
    				
    				Integer rotate = null;
    				
    				length = Float.parseFloat(scLigne.next());
    				height = Float.parseFloat(scLigne.next());
    				
    				if(scLigne.hasNext()) rotate = Integer.parseInt(scLigne.next());
    				
    				Wall w = new Wall(new Vector3d(x, y, z), length, height, this);
    				if(rotate != null) w.rotate90(rotate);
    				
    				this.envConfig.put("Wall", w);
    				this.alWall.add(w);
    				add(w);
    			}
    			else if(type == 'B')
    			{
    				float xf = Float.parseFloat(scLigne.next());
    				float yf = Float.parseFloat(scLigne.next());
    				float zf = Float.parseFloat(scLigne.next());
    				
    				Box b = new Box(new Vector3d(x, y, z), new Vector3f(xf, yf, zf),this);
    				
    				this.envConfig.put("Box", b);
    				this.alBox.add(b);
    				add(b);
    			}
    			
    			else if(type == 'A')
    			{
    				Arch a = new Arch(new Vector3d(x, y, z), this);
    				//a.rotate90(5);
    				this.alArch.add(a);
    				this.envConfig.put("Arch", a);
    				//add(a); d'après les consignes les arch font planter le programme
    			}
    			
    			else if(type == 'E')
    			{
    				//int nbRobots = Integer.parseInt(scLigne.next());
    				
    				//this.ennemis = new ArrayList<>(nbRobots);
    			
    				//for(int cpt=0; cpt<nbRobots; cpt++)
    				//{
    				RobotEnnemi ennemi = new RobotEnnemi(new Vector3d(x, y, z), "Ennemi ");
    				this.ennemis.add(ennemi);
    				add(ennemi);
    				//}
    				//System.out.println(ennemis);
				}
    			
    			scLigne.close();
    		}
		}
		
		sc.close();
		
		
		/* TP3 */
		// ball agent ne pas utiliser en mm temps que cherry agent car on doit désactiver le simulateur physique
		/*setUsePhysics(true);
		
		Color3f c = new Color3f(0.6f,0.5f,0.3f);
		int x = (int)(Math.random()*10);
		int z = (int)(Math.random()*10);
		
		add(new BallAgent(new Vector3d(x, 0, z), "ball", c,0.25f,0.25f)); // coord a tirer au hasard*/
		
		// cherry agent
		setUsePhysics(false);
		
		int x = (int)(Math.random()*10);
		int z = (int)(Math.random()*10);
		
		add(new CherryAgent(new Vector3d(x, 0, z), "cherry", 0.15f));
		//add(new RobotEnnemi(new Vector3d(x+5, 0, z+5), "Ennemi "));
		
		//System.out.println(this.envConfig.keySet());
	}
	
	/**
	 * Permet de définir le Simulator de l'environnement courant
	 * */
	public void setSimulator(Simulator simulator){
		this.simulator = simulator;
	}
	
	/**
	 * Permet de stopper la simulation notament depuis un robot
	 * */
	public void stopSimulation(){
		try{
			Thread.sleep(500);
		}
		catch(InterruptedException evt){}
		simulator.stopSimulation();
	}
}
