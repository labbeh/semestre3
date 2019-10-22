package simbad.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tp3.MyRobot;

import simbad.sim.Simulator;
import simbad.sim.World;

public class AgentControlGUI extends JPanel implements ActionListener, ChangeListener
{
	private JSlider speed;
	
	private int oldSpeed;
	
	private JButton left;
	private JButton right;
	
	RobotFollower robotFollower;
	
	public AgentControlGUI(World world, Simulator simulator)
	{
		// robot follower
		this.robotFollower = new RobotFollower(world, (MyRobot)simulator.getAgentList().get(0));

		oldSpeed = 0;
		
		// controle de la direction du robot
		this.left  = new JButton("left" );
		this.right = new JButton("right");
		
		// controle de la vitesse du robot
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
        
        panel2_1.add(speed);
        
        panel2.add(panel2_1);
        
        // ajout des deux groupes de boutons
        add(panel1);
        add(panel2);
		
		this.left.addActionListener(this);
		this.right.addActionListener(this);

		speed.addChangeListener(this);
    }
	

	@Override
	public void actionPerformed(ActionEvent evt)
	{	
		if	   (evt.getSource() == left ) this.robotFollower.robot.rotateY( Math.PI/8);
		else if(evt.getSource() == right) this.robotFollower.robot.rotateY(-Math.PI/8);
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
