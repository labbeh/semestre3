package statistiques;

/**
* @author frederic Serin, hugo labbé
* @version 2018-11-16
*/

public class Poisson
{
    /**
     * Paramètre de la loi de Poisson.
     * Si cette loi donne des résultats entiers, le paramètre peut être un réel
     * strictement positif
     */
    private double lambda;

    /**
     * la fabrique retourne une fusion en une loi de Poisson et une loi Exponentielle de paramètre lambda
     * sauf si lambda n'est pas acceptable.
     * @param lambda réel strictement positif
     * @return distribution de Poisson (ou null)
     */
    public static Poisson create(double lambda) {
        if (lambda>0) return new Poisson(lambda);

        return null;
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
     * Fonction de densité, probabilité d'avoir une valeur égale à k.
     * @param k valeur entière supérieure ou égale à 0
     * @return probabilité P(x=k) d'avoir k
     */
    public double p(double k) {
        long   trial    = Math.round(k);
        double resultat = 1.0;

        if (trial > 0) {

            for (long i=1; i<=trial; i++) {
                resultat = resultat * (1/lambda) / i;
            } // fin de la boucle
        } // fin du test if trial>0

        resultat = resultat * Math.exp(-(1/lambda));

        return resultat;
    } // retourne la proba pour x

    /* Methodes de classe */
    /**
     * Cette méthode exige un paramètre en argument qui représente
     * la distance moyenne générée par la loi exponentielle.
     * Si nous prenons 0.25, cela correspond à une loi de Poisson de paramètre lambda 4.
     * On calcule le nombre de tirages nécessaires pour atteindre 1
     * Ce qui fait un nombre de passages moyen de 4 sur l'intervalle.
     * On compare alors le nombre de passages réellement obtenu
     * par tirage au sort (random) avec
     * le résultat théorique de la loi de
     * Poisson de moyenne 4.
    * On obtient évidemment des résultats comparables.
     * @param args premier argument égal au pas (positif)
     */
    static public void main(String[] args)
    {
        try
        {
            Poisson loi = new Poisson(Double.parseDouble(args[0]));

            int compteur;

            // variable intermédiaire, distance franchie
            double etape;
            double distance;
            boolean continuer;

            // cette variable compte le nombre de franchissements par nombre d'étapes
            int[] foisFranchis = new int[30];

            // on teste notre programme 10000 fois
            for (int i=0; i<10000; i++) {
                // on compte le nombre d'évènements générés pendant l'intervalle de temps donné
                compteur = 0;
                // au début la distance est initialisée, puis incrémenté
                distance = 0.0;
                continuer = true;
                // tant qu'on n'a pas dépassé le seuil de 1
                while (continuer) {
                    etape = loi.next();
                    // si on dépasse le seuil de 1, on arrête
                    if ((distance+etape)>1) continuer=false;
                    else {
                        distance += etape;
                        compteur++;
                    }
                } // fin tant qu'on a pas parcouru 1
                foisFranchis[compteur]++; // c'est ici que on peut avoir un soucis si compteur trop grand
            } // fin des essais
            // affichage des résultats
            double resultat = 0;

            for (int k=0; k<20; k++) {
                resultat = (long)Math.round(loi.p(k)*10000.0);
                System.out.println("avec "+k+" on a "+foisFranchis[k]+" et on devrait avoir "+resultat);
            } // fin affichage des 20 valeurs premières

        }
        catch (Exception e) {
            System.out.println("Soucis lors de la construction de la loi de Poisson ou "+e);
        }
    } // fin méthode main

    /* Méthodes d'instance */

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

    /**
     * Fourni aléatoirement une distance
     * correspondant à la loi exponentielle.
     * @return valeur positive
     */
    public double next() {
        return (-Math.log(1.0 - Math.random())*(1/lambda));
    }

    /* ACCESSEURS */

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
}
