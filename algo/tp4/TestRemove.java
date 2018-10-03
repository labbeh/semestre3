import java.util.*;

public class TestRemove
{
	public static void main(String[] args)
	{
		LinkedList<Integer> lk = new LinkedList<Integer>();

		lk.add(12);
		lk.add(7);
		lk.add(22);
		lk.add(11);
		lk.add(5);

		ListIterator iter = lk.listIterator();

		System.out.println(" ETAPE 1 : " +lk);

		System.out.println( iter.next() );
		System.out.println( iter.next() );
		System.out.println( iter.next() );

		iter.remove();

		System.out.println( " ETAPE 2 : " + lk );

		System.out.println( iter.next() );

		System.out.println( iter.previous() );
		System.out.println( iter.previous() );

		iter.remove(); // dernier élément pointé par next ou remove

		System.out.println( " ETAPE 3 : " +lk );
	}
}