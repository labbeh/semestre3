import java.util.*;

public class Train implements Iterable<Vehicule>, Iterator<Vehicule>
{
	private Vehicule[] ensVehicule;
	private int 	   nbVehiucle ;

	private int cptIterator;

	public Train()
	{
		this.ensVehicule = new Vehicule[100];
		this.nbVehiucle  = 0				;

		this.cptIterator = 0;
	}

	public boolean ajouterVehicule(Vehicule v)
	{
		if(this.nbVehiucle == this.ensVehicule.length)
			return false;

		this.ensVehicule[this.nbVehiucle++] = v;
		return true;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for(int cpt=0; cpt<this.nbVehiucle; cpt++)
			sb.append("- " +this.ensVehicule[cpt].getCouleur()+ " - ");

		return sb.toString();
	}

	@Override
	public Iterator<Vehicule> iterator() { return this; } // ou crééer une classe interne IterTrain
														  // throw UnsupportedOperationException sur remove()

	@Override
	public boolean hasNext () { return this.cptIterator < this.nbVehiucle; }

	@Override
	public Vehicule next() { return this.ensVehicule[this.cptIterator++]; }

	public static void main(String[] args)
	{
		Train train = new Train();

		train.ajouterVehicule( new Vehicule("ROUGE") );
		train.ajouterVehicule( new Vehicule("VERT") );
		train.ajouterVehicule( new Vehicule("BLEU") );
		train.ajouterVehicule( new Vehicule("violet") );
		train.ajouterVehicule( new Vehicule("ROUGE") );
		train.ajouterVehicule( new Vehicule("ROUGE") );
		train.ajouterVehicule( new Vehicule("violet") );

		System.out.println(train);
		System.out.println();

		// partie iterable
		String couleur;
		Scanner sc = new Scanner(System.in);

		System.out.print("Couleur: ");
		couleur = sc.nextLine().toUpperCase();

		int cpt=0;
		for(Vehicule v: train)
		if(v.getCouleur().equals(couleur)) cpt++;

		System.out.println("Nombre de train " +couleur+ " : " +cpt);
	}
}