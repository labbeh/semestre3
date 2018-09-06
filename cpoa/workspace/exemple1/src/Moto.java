public class Moto extends DeuxRoues {

	public Moto(int lePrix) {
		super(lePrix);
		// TODO Auto-generated constructor stub
	}

	public int taxe() {
		// TODO Auto-generated method stub
		return (int) (this.prix * 0.2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Moto moto = new Moto(1000);
		
		System.out.println(moto);
	}

}
