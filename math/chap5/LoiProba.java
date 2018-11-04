/**
* @author hugo labbé, D2
* @version 2018-11
*/
public class LoiProba
{
    /**
    * Jeu de test de la classe LoiProba
    */
    public static void main(String[] args)
    {
        int[] xi = new int[]{3, 5, 4, 6};
        double[] pi = new double[]{0.2, 0.4, 0.3, 0.1};

        LoiProba loi = LoiProba.fabrique(xi, pi);

        System.out.println("////// Jeu de Test //////");

        System.out.println("Proba d'avoir 4:" +loi.probabilite(4));
        System.out.println("Proba d'avoir 3:" +loi.probabilite(3));

        System.out.println("Esperance: "        +loi.esperance());
        System.out.println("Moment d'ordre 2: " +loi.moment2  ());
        System.out.println("Variance: "         +loi.variance ());
        System.out.println("Ecart type: "       +loi.ecartType());

        System.out.println("////// Fin des tests //////");

        // cas qui lancerai une IllegalArgumentException:
        //System.out.println(loi.probabilite(43));
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
        // on vérifie que les deux tableau ont bien la même taille ...
        if(xi.length != pi.length) return null;

        // ... que le contenue de pi est bien compris entre 0 et 1 (proba) ...
        for(int i=0; i<pi.length; i++)
            if(pi[i] > 1 || pi[i] < 0) return null;

        // ... que l'on a bien qu'une seul fois le même elt dans xi ...
        int elt = 0;
        for(int i=0; i<xi.length; i++)
        {
            elt = xi[i];

            for(int i2=0; i2<xi.length; i2++)
                if(xi[i2] == elt && i2 != i) return null;
        }

        // ... et que la somme des proba donne bien 1
        double sommeP = 0.0;
        for(int i=0; i<pi.length; i++)
            sommeP += pi[i];

        // on cast en int pour éviter les pb de précision du type "double"
        if((int)sommeP != 1) return null;

        return new LoiProba(xi, pi);
    }

    /**
    * Tableau d'entier contenant des valeurs: une valeur ne peut être présente qu'une seule fois
    */
    private int[] xi;

    /**
    * Tableau de réels contenant les proba des valeurs xi du tableau de valeur à indice égal
    */
    private double[] pi;

    /**
    * Construit une LoiProba à partir d'un tableau de valeur entières xi et un tableau de réels contenants les proba pi
    */
    private LoiProba(int[] xi, double[] pi)
    {
        this.xi = xi;
        this.pi = pi;
    }

    /**
    * Retroune la proba de la valeur passée en paramètre si elle est valide
    * @param X, valeur dont la proba est à rechercher
    * @return probabilite de X (valeur en paramètre)
    * @throws IllegalArgumentException si la valeur passée en paramètre n'existe pas dans xi
    */
    public double probabilite(int valeur)
    {
        int ind = -1;
        for(int i=0; i<xi.length; i++)
            if(xi[i] == valeur) return pi[i];

        // si le paramètre est invalide
        throw new IllegalArgumentException("Valeur X introuvable dans cette loi de proba");
    }

    /**
    * Calcul l'espérance
    * @return valeur de l'espérance
    */
    public double esperance()
    {
        double dRet = 0.0;

        for(int i=0; i<xi.length; i++) dRet += xi[i] * pi[i];

        return dRet;
    }

    /**
    * Calcul le moment d'ordre 2
    * @return valeur du moment d'ordre 2
    */
    public double moment2()
    {
        double dRet = 0.0;

        for(int i=0; i<xi.length; i++) dRet += Math.pow(xi[i],2) * pi[i];

        return dRet;
    }

    /**
    * Calcul la variance
    * @return valeur de la variance
    */
    public double variance()
    {
        double dRet = 0.0;
        double esperance = esperance();

        for(int i=0; i<xi.length; i++)
            dRet += Math.pow(xi[i]-esperance, 2) * pi[i];

        return dRet;
    }

    /**
    * Calcul l'écart-type
    * @return valeur de l'écart-type
    */
    public double ecartType()
    {
        return Math.sqrt(variance());
    }
}