public class Puissance
{
	public double puissance(double val, int exp)
	{
		if(exp == 0) return 1;
		if(exp == 1) return val;

		double pow = val;

		for(int cpt=0; cpt<exp; cpt++) pow *= val;

		return pow;
	}

	public double puissanceRec(double val, int exp)
	{
		if(exp == 0) return 1;
		if(exp == 1) return val;

		

		return pow;
	}
}