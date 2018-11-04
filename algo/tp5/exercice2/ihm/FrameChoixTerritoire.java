package exercice2.ihm;

import exercice2.Controleur;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.util.Vector;

import java.awt.event.*;

public class FrameChoixTerritoire extends JFrame implements ActionListener
{
	private Controleur ctrl;

	private JComboBox<String> liste;

	private JButton btnOk	  ;
	private JButton btnAnnuler;

	private JPanel panelPrinc;
	private JPanel panelBtns ;

	public FrameChoixTerritoire( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.setTitle("Choix du territoire...");
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.liste = new JComboBox<>(listeTerritoires());

		this.btnOk 		= new JButton("Ok"	   );
		this.btnAnnuler = new JButton("Annuler");

		this.panelPrinc = new JPanel(new GridLayout(1, 2));
		this.panelBtns  = new JPanel(new GridLayout(1, 2));

		this.panelPrinc.add(new JLabel("Territoire: "));
		this.panelPrinc.add(this.liste);

		this.panelBtns.add(this.btnOk);
		this.panelBtns.add(this.btnAnnuler);

		this.add(this.panelPrinc);
		this.add(this.panelBtns, BorderLayout.SOUTH);

		this.btnOk.addActionListener(this);
		this.btnAnnuler.addActionListener(this);


		this.pack();
	}

	private Vector<String> listeTerritoires()
	{
		List<String> nomsTerritoires = this.ctrl.getListeNomTerritoires();
		Vector<String> ret = new Vector<>();

		for(String elt: nomsTerritoires)
			ret.add(new String(elt));

		return ret;
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == this.btnOk)
		{
			this.setVisible(false);
			this.ctrl.setZone(this.liste.getSelectedItem().toString());

		}
		else this.setVisible(false);

		this.ctrl.reInitIhm();
	}

}
