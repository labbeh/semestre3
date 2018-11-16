//package statistiques;

/**
* @author hugo labbé
*/
public class TestPoisson
{
    public static void main(String[] args)
    {
        Poisson maLoi = new Poisson(8);

        System.out.println("P(4)= " +maLoi.p(4));
        //System.out.println(maLoi.p(5));
        //System.out.println(maLoi.p(6));
        //System.out.println(maLoi.cumulative(2));

        int k = 5;
        // k = 3;
        double cumul = 0.0;

        while(cumul <= 0.99)
        {
            k++;
            //System.out.println(k);
            cumul += maLoi.p(k);
            //System.out.println(cumul);
        }

        System.out.println(cumul + " est atteint au k " +k);
    }
}
