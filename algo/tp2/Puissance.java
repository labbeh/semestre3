public class Puissance
{
	public static double puissance(double val, int exp)
	{
		if(exp == 0) return 1;
		if(exp == 1) return val;

		double pow = val;

		for(int cpt=0; cpt<exp; cpt++) pow *= val;

		return pow;
	}

	public static double puissanceRec(double val, int exp)
	{
		if(exp == 0) return 1;
		if(exp == 1) return val;

		

		return val * puissanceRec(val, exp-1);
	}

	public static void main(String[] args)
	{
		System.out.println(puissanceRec(2, 4));
	}
}