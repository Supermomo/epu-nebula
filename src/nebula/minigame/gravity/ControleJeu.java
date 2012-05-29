package nebula.minigame.gravity;

import org.newdawn.slick.Input;

public class ControleJeu {

	ModeleJeu modeleJeu;
	
	
	public ControleJeu() {
		this(null);
	}
	public ControleJeu(ModeleJeu modeleJeu) {
		this.modeleJeu = modeleJeu;
	}

	public void setModele(ModeleJeu modeleJeu) {
		this.modeleJeu = modeleJeu;
	}

	public void inputJoueur(Input input, int delta) {
		// -- Déplacement Hero
		// Déplacement à gauche
		if (input.isKeyDown(Input.KEY_LEFT)) {
			modeleJeu.deplacementGauche(delta);
		}
		// Déplacement à droite
		else if (input.isKeyDown(Input.KEY_RIGHT)) {
			modeleJeu.deplacementDroite(delta);
		}

		// Changer la gravité
		if(input.isKeyPressed(Input.KEY_SPACE))
			modeleJeu.inverserGravite();
		
		modeleJeu.appliquerGravite(delta);
		// -- Fin - déplacement Hero

	}
}
