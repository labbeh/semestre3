package exercice3;

//import exercice3.metier.*;
import exercice3.ihm.*;
import exercice2.metier.*;

import java.util.List;

public class Controleur
{
	private Gui ihm;

	private List<Territoire> listTerritoires;

    public Controleur()
    {
    	this.ihm = new Gui(this);
    }

    public void ouvrir(String urlFich)
    {
    	this.listTerritoires = GestionFichier.lireTerritoires(urlFich);
    }

    public void verifZone(int x, int y)
    {
        if(this.listTerritoires == null || this.listTerritoires.isEmpty()) return;

        for(Territoire terr: this.listTerritoires)
        {
            if(x >= terr.getMinX() && x <= terr.getMaxX() && y >= terr.getMinY() && y<= terr.getMaxY())
            {
                this.setZoneCurseur(terr.getNom());
                break;
            }

            else this.setZoneCurseur("");
            /*try{Thread.sleep(50);}
            catch(InterruptedException evt){}*/
        }
    }

    public void setZoneCurseur(String nomTerr)
    {
        this.ihm.setSelectZone(nomTerr);
    }

    public static void main(String[] args)
    {
    	new Controleur();
    }
}
