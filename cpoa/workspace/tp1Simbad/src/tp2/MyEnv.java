package tp2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Arch;
import simbad.sim.BlockWorldObject;
import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;
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
	

	public MyEnv()
	{
		/*Wall w1 = new Wall(new Vector3d(9, 0, 0), 19, 1, this);
		w1.rotate90(1);
		add(w1);
		
		Wall w2 = new Wall(new Vector3d(-9, 0, 0), 19, 2, this);
		w2.rotate90(1);
		add(w2);
		
		Wall w3 = new Wall(new Vector3d(0, 0, 9), 19, 1, this);
		add(w3);
		
		Wall w4 = new Wall(new Vector3d(0, 0, -9), 19, 2, this);
		add(w4);
		
		Box b1 = new Box(new Vector3d(-6, 0, -3), new Vector3f(1, 1, 1),this);
		add(b1);*/
		
		this.envConfig = new HashMap<>();
		
		this.alArch = new ArrayList<>();
		this.alBox  = new ArrayList<>();
		this.alWall = new ArrayList<>();
		
		InputStream ips = this.getClass().getResourceAsStream("/myenv.txt");
		InputStreamReader ipsr = new InputStreamReader(ips);
		Scanner sc = new Scanner(ipsr);
		
		while(sc.hasNextLine())
		{
			Scanner scLigne = new Scanner(sc.nextLine());
			
			char type = scLigne.next().charAt(0);
			
			if(type == 'R')
			{
				int nbRobots = Integer.parseInt(scLigne.next());
				
				this.listeRobots = new ArrayList<>(nbRobots);
				
				for(int cpt=0; cpt<nbRobots; cpt++)
				{
					this.listeRobots.add(new MyRobot(new Vector3d((int)(Math.random()*cpt), 0, (int)(Math.random()*cpt)), "MyRobot " +cpt));
					add(this.listeRobots.get(cpt));
				}
				
				scLigne.close();
				
				if(!sc.hasNextLine()) break;
				
				scLigne = new Scanner(sc.nextLine());
				type = scLigne.next().charAt(0);
			}
			
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
				add(a);
			}
			
			scLigne.close();
		}
		
		sc.close();
		
		//System.out.println(this.envConfig.keySet());
		
		//add(new MyRobot(new Vector3d(0, 0, 0),"mon robot 1"));
		//add(new MyRobot(new Vector3d(3, 0, 3),"mon robot 1"));
		//add(new MyRobot(new Vector3d(4, 0, 4),"mon robot 1"));
		//add(new MyRobot(new Vector3d(5, 0, 5),"mon robot 1"));
		/*add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));
		add(new RobotSonars(new Vector3d(1, 0, 1),"robot sonar"));*/
		
		/*add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));
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
		add(new RobotBumpers(new Vector3d(1,0,1), "robot bumper"));*/
		
	}

}
