public class Test
{
	public static void main(String[] args)
	{
		String s = "ceci est un message";
		byte[] tab = s.getBytes();

		for(int cpt=0; cpt<tab.length; cpt++)
		{
			String mot = Integer.toBinaryString(tab[cpt]);

			byte[] motEntier = new byte[]{(byte)Integer.parseInt(mot, 2)};

			System.out.println(new String(motEntier));

		}
	}
}