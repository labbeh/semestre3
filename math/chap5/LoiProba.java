public class LoiProba
{
    /**
    * Jeu de test de la classe LoiProba
    */
    public static void main(String[] args)
    {
        int[] xi = new int[]{3, 5, 4, 6};
        double[] pi = new double[]{0.2, 0.4, 0.3, 0.1};

        /*double sommeP = 0.0;
        for(int i=0; i<pi.length; i++)
            sommeP += pi[i];
        System.out.println((int)sommeP);*/
        LoiProba loi = LoiProba.fabrique(xi, pi);
        //System.out.println(loi.probabilite(43));

        System.out.println("Esperance: " +loi.esperance());
    }

    /**
    * construit une loi de proba (xi, pi)
    * @param xi: tableau de valeur d'évènement, pi: probabilite que l'évènement
    *             au même indice que xi se produise.
    *             Les 2 tableaux doivent être de même longueur et les pis compris
    *             entre 0 et 1
    * @return LoiProba si la loi est valide, null si invalide
    */
    public static LoiProba fabrique(int[] xi, double[] pi)
    {
        if(xi.length != pi.length) return null;

        for(int i=0; i<pi.length; i++)
            if(pi[i] > 1 || pi[i] < 0) return null;

        double sommeP = 0.0;
        for(int i=0; i<pi.length; i++)
            sommeP += pi[i];

        // on cast en int pour éviter les pb de précision du type "double"
        if((int)sommeP != 1) return null;

        return new LoiProba(xi, pi);
    }

    private int[] xi;
    private double[] pi;

    private LoiProba(int[] xi, double[] pi)
    {
        this.xi = xi;
        this.pi = pi;
    }

    /**
    * @param X
    * @return probabilite de X (valeur en paramètre)
    */
    public double probabilite(int valeur)
    {
        int ind = -1;
        for(int i=0; i<this.xi.length; i++)
        {
            if(this.xi[i] == valeur)
            {
                ind = i;
                break;
            }
        }

        if(ind == -1) throw new IllegalArgumentException("Valeur X introuvable dans cette loi de proba");

        return this.pi[ind];
    }

    /**
    * Calcul l'espérance
    * @return valeur de l'espérance
    */
    public double esperance()
    {
        double dRet = 0.0;

        for(int i=0; i<this.xi.length; i++) dRet += this.xi[i] * this.pi[i];

        return dRet;
    }

    /**
    * Calcul le moment d'ordre 2
    * @return valeur du moment d'ordre 2
    */
    public double moment2()
    {
        return 0.0;
    }

    /**
    * Calcul la variance
    * @return valeur de la variance
    */
    public double variance()
    {
        double dRet = 0.0;
        double esperance = esperance();

        for(int i=0; i<this.xi.length; i++)
            dRet += Math.pow(xi[i]-esperance, 2) * pi[i];

        return dRet;
    }

    /**
    * calcul l'écart-type
    * @return valeur de l'écart-type
    */
    public double ecartType()
    {
        return 0.0;
    }
}
