package statistiques;

/**
 * La loi exponentielle permet de générer une durée
 * entre deux évènements.
 * Elle est complémentaire de la loi de Poisson qui donne la probabilité de voir
 * des évènements sur un intervalle de temps donné.
 * La méthode main permet de tester la similarité entre ces deux lois.
 * @author frederic Serin
 * @version 2017-10-30
 * @since amélioration de la version du 2 janvier 2015
 */
public class Exponentielle {

    /**
     * distance moyenne entre deux évènements
     * Valeur strictement positive.
     */
    private double pas;

    /**
     * Construit une loi exponentielle avec un pas positif
     * qui représente l'intervalle moyen entre deux évènements générés.
     * Si on passe un argument négatif, il sera forcé égal à 1.
     * @param pas valeur strictement positive
     */
    public Exponentielle(double pas) {
        if (pas<=0) pas = 1.0;
        this.pas = pas;
    }

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
            double        longueur         = new Double(args[0]);
            Exponentielle loiExponentielle = new Exponentielle(longueur);
            Poisson       loi              = new Poisson(1/longueur);

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
                    etape = loiExponentielle.next();
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

    /**
     * Fourni aléatoirement une distance
     * correspondant à la loi exponentielle.
     * @return valeur positive
     */
    public double next() {
        return (-Math.log(1.0 - Math.random())*pas);
    }
} // fin classe Test_expo
