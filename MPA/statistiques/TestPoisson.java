package statistiques;

public class TestPoisson
{
    public static void main(String[] args)
    {
        //double moyennePoisson = 2;

        Poisson maLoi = new Poisson(2.0);

        System.out.println(maLoi.p(4));
        //System.out.println(maLoi.cumulative(2));

        int k = -1;
        double cumul = 0.0;

        while(cumul < 0.99)
        {
            k++;
            cumul += maLoi.p(k);
        }

        System.out.println(cumul + " est atteint au k " +k);
    }
}
