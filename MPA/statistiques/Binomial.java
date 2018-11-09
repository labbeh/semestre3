package statistiques;

/**
 * La distribution binomiale est une
 * distribution qui fourni la probabilité pour qu'un évènement arrive k fois
 * sachant qu'il y aura n tirages indépendants avec une même probabilité.
 * @author Frederic Serin, departement Informatique, Le Havre
 * @version 0.0
 */
public class Binomial {

    /**
     * Nombre de tirages.
     */
    private long n;

    /**
     * Probabilité d'occurence.
     */
    private double p;

    /**
     * Constructeur d'une distribution binomiale.
     * @param n valeur supérieure ou égale à 1
     * @param p probabilité comprise entre 0 et 1
     */
    public Binomial (long n, double p) {
        this.n = n;
        this.p = p;
    } // fin constructeur

    /**
     * Moyenne de la distribution binomiale.
     * @return la moyenne égale à np
     */
    public double getMean () {
        return (n*p);
    } // moyenne

    /**
     * Variance de la distribution binomiale.
     * @return la variance
     */
    public double getVariance () {
        return (n*p*(1.0-p));
    } // fin get variance

    /**
     * Fonction de densité, .
     * @param k valeur entière supérieure ou égale à 0
     * @return probabilité P(x=k) d'avoir k
     */
    public double p(double k) {
        long trial = Math.round(k);
        if (trial>n) return 0.0;

        return (cumulative(trial)-cumulative(trial-1));
    } // retourne la proba pour x

    /**
     * Valeur cumulée de la distribution de 0 à k.
     * @param k limite du cumul
     * @return valeur cumulée comprise entre 0 et 1
     */
    public double cumulative (double k) {
        if      (k<0) return 0;
        else if (k>n) return 1.0;

        long   trial         = Math.round(k);
        double resultat      = Math.pow((1-p),n);
        double intermediaire = resultat;

        if (trial==0) return resultat;

        long j = n;

        for (long i=1; i<=trial; i++) {
            intermediaire = intermediaire / (1-p) * p * j /i;
            j--;
            resultat += intermediaire;
        } // fin des cumuls
        return resultat;
    } // fin méthode cumulative
    
} // fin de la classe Binomiale
