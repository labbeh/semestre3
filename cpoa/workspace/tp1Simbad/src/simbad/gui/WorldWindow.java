/*
 *  Simbad - Robot Simulator
 *  Copyright (C) 2004 Louis Hugues
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 -----------------------------------------------------------------------------
 * $Author: sioulseuguh $ 
 * $Date: 2005/01/08 16:51:30 $
 * $Revision: 1.3 $
 * $Source: /cvsroot/simbad/src/simbad/gui/WorldWindow.java,v $
 */
package simbad.gui;

import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import simbad.sim.Simulator;
import simbad.sim.World;
import tp3.MyRobot;

/**
 * This window is used to visualise the 3D world.
 */
public final class WorldWindow extends JInternalFrame{
  
	private static final long serialVersionUID = 1L;
	World world;
	RobotFollower robotFollower;
	private Thread tFocus;
 
    public WorldWindow(World world, Simulator simulator) {
        super("world");
        this.world = world;
        this.robotFollower = new RobotFollower(world, (MyRobot)simulator.getAgentList().get(0));
        initialize();
    }

    private void initialize() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add("Center",world.getCanvas3D());
        setContentPane(panel);
        setSize(400, 400);
        setResizable(true);
        
        setFocusable(true);

        addKeyListener(new KeyboardListener(robotFollower));
        tFocus = new Thread(new GestionRequest());
        tFocus.start();
        //tFocus.start();
    }
    
    /**
     * Classe interne pour crééer un Thread qui va donner en permanence le focus sur la JInternalFrame pour
     * pouvoir controler le robot au clavier juste en cliquant sur la JInternalFrame WorldWindow
     * */
    private class GestionRequest implements Runnable{

		@Override
		public void run(){
			while(true){
				requestFocus();
				
				try{
					Thread.sleep(500);
				}
				catch(Exception evt){}
			}
		}
    }
}