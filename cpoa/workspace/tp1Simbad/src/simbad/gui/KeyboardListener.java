package simbad.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter
{
	// attributs de classe
	private static int LEFT_KEY  = 37;
	private static int RIGHT_KEY = 39;
	
	// robot follower pour controler le robot au clavier
	RobotFollower robotFollower;

	KeyboardListener(RobotFollower robotFollower)
	{
		this.robotFollower = robotFollower;
	}

	@Override
	public void keyPressed(KeyEvent evt)
	{
		if	   (evt.getKeyCode() == LEFT_KEY )this.robotFollower.robot.rotateY(Math.PI/12);
		else if(evt.getKeyCode() == RIGHT_KEY)this.robotFollower.robot.rotateY(-Math.PI/12);
	}
}
