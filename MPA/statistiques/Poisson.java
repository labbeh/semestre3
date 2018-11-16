//package statistiques;

/**
 * La distribution de Siméon Poisson est une
 * distribution qui fourni la probabilité du nombre d'évènements qui peuvent
 * apparaitre sur un intervalle de temps donné.
 * C'est une loi discrète c'est-à-dire répartie sur des entiers.
 * @author Frederic Serin, departement Informatique, Le Havre
 * @version 1.0, 2017-11-04
 */
public class Poisson {

    /**
     * Paramètre de la loi de Poisson.
     * Si cette loi donne des résultats entiers, le paramètre peut être un réel
     * strictement positif
     */
    private double lambda;

    /**
     * la fabrique retourne une loi de Poisson de paramètre lambda
     * sauf si lambda n'est pas acceptable.
     * @param lambda réel strictement positif
     * @return distribution de Poisson (ou null)
     */
    public static Poisson create(double lambda) {
        Poisson poisson = null;

        if (lambda>0) {
            poisson = new Poisson(lambda);
        }

        return poisson;
    }

    /**
     * Constructeur d'une distribution de Poisson.
     * appelé via la fabrique create afin d'éviter tout soucis avec la valeur de lambda
     * @param lambda moyenne réelle strictement positive
     */
    Poisson (double lambda) {
        // il faut que lambda soit supérieur à 0
        this.lambda = lambda;
    } // fin constructeur

    /**
     * Paramètre lambda de la loi de Poisson
     * @return paramètre supérieur à 0.0
     */
    public double getLambda () {
        return lambda;
    } // fin accesseur

    /**
     * Moyenne de la distribution de Poisson.
     * @return la moyenne égale à lambda
     */
    public double getMean () {
        return lambda;
    } // moyenne

    /**
     * Variance de la distribution de Poisson.
     * @return la variance égale à lambda
     */
    public double getVariance () {
        return lambda;
    } // variance

    /**
     * Fonction de densité, probabilité d'avoir une valeur égale à k.
     * @param k valeur entière supérieure ou égale à 0
     * @return probabilité P(x=k) d'avoir k
     */
    public double p(double k) {
        long   trial    = Math.round(k);
        double resultat = 1.0;

        if (trial > 0) {

            for (long i=1; i<=trial; i++) {
                resultat = resultat * lambda / i;
            } // fin de la boucle
        } // fin du test if trial>0

        resultat = resultat * Math.exp(-lambda);

        return resultat;
    } // retourne la proba pour x

    /**
     * Valeur cumulée de la distribution de 0 à k.
     * @param k limite du cumul
     * @return valeur cumulée comprise entre 0 et 1
     */
    public double cumulative (double k) {
        double cumul = 0.0;
        long   trial = Math.round(k);

        // trial ne devrait pas être inférieur à zéro !
        if (trial <0) return cumul;

        for (long i=0; i<=trial; i++) {
            cumul += p(i);
        }

        return cumul;
    } // fin méthode cumulative
} // fin de la classe Poisson
