import javax.swing.*;
import java.awt.*;

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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		//this.setVisible(false);
	}

	private String[] listeTerritoires()
	{
		String[] tab = new String[]{"Asie", "Europe", "Amérique", "Afrique"};

		return tab;
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		System.out.println(this.liste.getSelectedItem());
	}

	public static void main(String[] args)
	{
		new FrameChoixTerritoire(null).setVisible(true);
	}
}