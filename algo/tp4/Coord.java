public class Coord implements Comparable<Coord>
{
	double x;
	double y;

	public Coord ( double x, double y )
	{
		this.x = x;
		this.y = y;
	}

	public double getX(){ return this.x; }
	public double getY(){ return this.y; }

	public int compareTo(Coord c)
	{
		return Double.compare(this.y, c.y);
	}

}