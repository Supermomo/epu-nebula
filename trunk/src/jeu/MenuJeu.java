/*  Classe de menu de lancement de l'exemple de jeu.
 *  Cette classe h�rite de la classe abstraite MenuAbstrait en d�finissant les m�thodes :
 *     - nomOptions qui renvoie la liste des options possibles pour le menu 
 *     - lancerOption qui associe une action � chaque option du menu
 *     - wavAccueil() qui renvoie le nom du fichier wav lu lors de l'accueil dans le menu
 *     - wavAide() qui renvoie le nom du fichier wav lu lors de l'activation de la touche F1
 */

package jeu; 

import javax.swing.JOptionPane;

import nebula.core.NebulaGame;
import devintAPI.MenuAbstrait;

public class MenuJeu extends MenuAbstrait
{
    private static final long serialVersionUID = 1L;

    /** constructeur
	 * @param title : le nom du jeu 
	 */
	public MenuJeu(String title) {
		super(title);
	}

	/** renvoie le nom des options du menu
     * vous pouvez d�finir autant d'options que vous voulez
     **/
	protected String[] nomOptions() {
		String[] noms = {"Jouer","Options","Scores","Quitter"};
		return noms;
	}

	/** lance l'action associ�e au bouton n�i
	 * la num�rotation est celle du tableau renvoy� par nomOption
	 */
	protected void lancerOption(int i) {
		switch (i){  
		case 0 : 
		    NebulaGame.startNebulaGame();
			break;
		case 1 : new Option(nomJeu + ": gestion des options");break;
		//case 2 : new Fichier(nomJeu + ": pour �crire dans un fichier");break;
		case 3 : System.exit(0);
		default: System.err.println("action non d�finie");
		}
	}

	@Override
	protected String wavAccueil() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String wavRegleJeu() {
		// TODO Auto-generated method stub
		return null;
	} 
}
