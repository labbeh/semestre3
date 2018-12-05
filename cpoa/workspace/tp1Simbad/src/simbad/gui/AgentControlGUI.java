package simbad.gui;

import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tp3.MyRobot;

import simbad.sim.Agent;
import simbad.sim.SimpleAgent;
import simbad.sim.Simulator;
import simbad.sim.World;

public class AgentControlGUI extends JPanel implements ActionListener, KeyListener, ChangeListener
{
	private JSlider direction;
	private JSlider speed;
	
	private int prevPosDirection;
	private int prevPosSpeed;
	
	private int oldSpeed;
	
	private JButton left;
	private JButton right;
	
	/*private JButton more;
	private JButton less;
	private JButton def;*/
	
	private RobotFollower robotFollower;
	private World world;
	private Font smallFont;
	
	// attributs de classe
	private static int LEFT_KEY  = 37;
	private static int RIGHT_KEY = 39;

	
	public AgentControlGUI(World world, Simulator simulator)
	{
		// robot follower
		this.robotFollower = new RobotFollower(world, (MyRobot)simulator.getAgentList().get(0));
		//this.prevPosDirection = 0;
		//this.prevPosSpeed = 0;
		oldSpeed = 0;
		
		// controle de la direction du robot
		this.left  = new JButton("left" );
		this.right = new JButton("right");
		//this.direction = new JSlider(1, 20, 10);
		
		// controle de la vitesse du robot
		/*this.more = new JButton("more");
		this.less = new JButton("less");
		this.def  = new JButton("default");*/
		this.speed = new JSlider(0, 20, 0);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        // View Buttons
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Agent direction:"),BorderFactory.createEmptyBorder()));
        JPanel panel1_1 = new JPanel();
        panel1_1.setLayout(new BoxLayout(panel1_1, BoxLayout.X_AXIS));
        
        panel1_1.add(this.left);
        panel1_1.add(this.right);

        panel1.add(panel1_1);
             
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Agent Speed:"),
                BorderFactory.createEmptyBorder()));
        JPanel panel2_1 = new JPanel();
        panel2_1.setLayout(new BoxLayout(panel2_1, BoxLayout.X_AXIS));
        
       // panel2_1.add(this.more);
        //panel2_1.add(this.less);
        //panel2_1.add(this.def );
        panel2_1.add(speed);
        
        panel2.add(panel2_1);
        
        // ajout des deux groupes de boutons
        add(panel1);
        add(panel2);
		
		this.left.addActionListener(this);
		this.right.addActionListener(this);
		//this.direction.addChangeListener(this);
        
		/*this.more.addActionListener(this);
		this.less.addActionListener(this);
		this.def.addActionListener(this);*/
		speed.addChangeListener(this);
		
		left.addKeyListener(this);
		right.addKeyListener(this);
		
        this.world = world;
        this.smallFont = new Font("Arial",Font.PLAIN,11);
    }

	@Override
	public void actionPerformed(ActionEvent evt)
	{	
		if	   (evt.getSource() == left ) this.robotFollower.robot.rotateY(Math.PI/8);
		else if(evt.getSource() == right) this.robotFollower.robot.rotateY(-Math.PI/8);
		/*else if(evt.getSource() == more ) this.robotFollower.robot.incSpeed();
		else if(evt.getSource() == less ) this.robotFollower.robot.decSpeed();
		else if(evt.getSource() == def  ) this.robotFollower.robot.resetSpeed();*/
	}


	@Override
	public void keyPressed(KeyEvent evt)
	{
		if	   (evt.getKeyCode() == LEFT_KEY)this.robotFollower.robot.rotateY(Math.PI/8);
		else if(evt.getKeyCode() == RIGHT_KEY)this.robotFollower.robot.rotateY(-Math.PI/8);
		//System.out.println("keyPressed event");
		
	}


	@Override
	public void keyReleased(KeyEvent arg0)
	{
		//System.out.println("keyReleased event");
		
	}


	@Override
	public void keyTyped(KeyEvent arg0)
	{
		//System.out.println("keyTyped event");
		
	}


	@Override
	public void stateChanged(ChangeEvent evt){
		if(evt.getSource() == speed){
			if	   (oldSpeed < speed.getValue()) robotFollower.robot.incSpeed();
			else if(oldSpeed > speed.getValue()) robotFollower.robot.decSpeed();
			
			oldSpeed = speed.getValue();
		}
		
	}

}
