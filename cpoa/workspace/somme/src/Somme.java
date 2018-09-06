
public class Somme {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try
		{
			if(args.length == 2)
				System.out.println(Integer.parseInt(args[0]) + Integer.parseInt(args[1]));
			else
				System.out.println("Passer 2 paramètres entiers");
		}
		catch(NumberFormatException evt){System.out.println("Passer 2 paramètres entiers");}

	}

}
