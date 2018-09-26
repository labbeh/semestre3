import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.LinkedList;

import java.util.Collections;

import java.util.ListIterator;


public class ArrayVSLinked
{
	public static void main(String[] a)
	{
		long   tp1, tp2;
		double maxY;

		String test;

		ArrayList <Coord> alCoord = new ArrayList <Coord>();
		LinkedList<Coord> lkCoord = new LinkedList<Coord>();

		ListIterator li = lkCoord.listIterator();




		// remplissage des 2 collections
		for (double x=-5000; x<=5000; x+=0.1)
		{
			alCoord.add ( new Coord ( x, sin(x) + 2 * cos (x) ) );
			lkCoord.add ( new Coord ( x, sin(x) + 2 * cos (x) ) );
		}


		// Chaine pour savoir quel Test on veut lancer.
		test = "45";

		// Recherche de Y maximum dans l'ArrayList avec un get
		if ( test.indexOf ("1") != -1 )
		{
			tp1 = System.nanoTime();

			maxY = alCoord.get(0).getY();
			for ( int cpt=1; cpt<alCoord.size(); cpt ++)
				if ( alCoord.get(cpt).getY() > maxY ) maxY = alCoord.get(cpt).getY();

			tp2 = System.nanoTime();

			System.out.println ( "TEST 1 : " + maxY + " en " + String.format ("%,16d",(tp2-tp1) ) + " ns" );
		}


		Collections.sort(lkCoord);
		// Recherche de Y maximum dans la LinkedList avec un get  (x2)
		if ( test.indexOf ("2") != -1 )
		{
			tp1 = System.nanoTime();

			maxY = lkCoord.get(0).getY();
			for ( int cpt=1; cpt<lkCoord.size(); cpt ++)
				if ( lkCoord.get(cpt).getY() > maxY ) maxY = lkCoord.get(cpt).getY();

			tp2 = System.nanoTime();

			System.out.println ( "TEST 2 : " + maxY + " en " + String.format ("%,16d",(tp2-tp1) ) + " ns" );
		}

		// Recherche de Y maximum dans la LinkedList avec un get (x1)
		if ( test.indexOf ("3") != -1 )
		{
			double temp;
			tp1 = System.nanoTime();

			maxY = lkCoord.get(0).getY();
			for ( int cpt=1; cpt<lkCoord.size(); cpt ++)
			{
				temp = lkCoord.get(cpt).getY();
				if ( temp > maxY ) maxY = temp;
			}

			tp2 = System.nanoTime();

			System.out.println ( "TEST 3 : " + maxY + " en " + String.format ("%,16d",(tp2-tp1) ) + " ns" );
		}

		// Recherche de Y maximum dans l'ArrayList avec un for-each in
		if ( test.indexOf ("4") != -1 )
		{
			tp1 = System.nanoTime();

			maxY = alCoord.get(0).getY();
			for ( Coord val:alCoord)
				if ( val.getY() > maxY ) maxY = val.getY();

			tp2 = System.nanoTime();

			System.out.println ( "TEST 4 : " + maxY + " en " + String.format ("%,16d",(tp2-tp1) ) + " ns" );
		}

		// Recherche de Y maximum dans la LinkedList avec un for-each in
		if ( test.indexOf ("5") != -1 )
		{
			tp1 = System.nanoTime();

			maxY = lkCoord.get(0).getY();
			for ( Coord val:lkCoord)
				if ( val.getY() > maxY ) maxY = val.getY();

			tp2 = System.nanoTime();

			System.out.println ( "TEST 5 : " + maxY + " en " + String.format ("%,16d",(tp2-tp1) ) + " ns" );
		}
	}
}