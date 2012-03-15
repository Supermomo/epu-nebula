package nebula.minigame.gravity;

import org.newdawn.slick.Input;

public class ControleJeu {
	
	ModeleJeu modeleJeu;
	
	public ControleJeu(ModeleJeu modeleJeu) {
		this.modeleJeu = modeleJeu;
	}
	
	public void inputJoueur(Input input, int delta) {

		// -- Déplacement Hero
		// Déplacement à gauche
		if (input.isKeyDown(Input.KEY_LEFT)) {
			modeleJeu.deplacementGauche(delta);
		}
		// Déplacement à droite
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			modeleJeu.deplacementDroite(delta);
		}
		// Déplacement en haut
		if (input.isKeyDown(Input.KEY_UP)) {
			modeleJeu.deplacementHaut(delta);
		}
		// Déplacement en bas
		if (input.isKeyDown(Input.KEY_DOWN)) {
			modeleJeu.deplacementBas(delta);
		}
		// Courrir
		if(input.isKeyDown(Input.KEY_LSHIFT))
			modeleJeu.courrir();
		// Changer la gravité
		if(input.isKeyPressed(Input.KEY_SPACE))
			modeleJeu.inverserGravite();
		else {
	/*		
	 * // Le personnage s'arrête
	 * if(!hero.isStill() && hero.getStill()<35) hero.incStill();
	 * else if(hero.getStill()!=-1) hero.setAnimStill();
	 */
		}
		// -- Fin - déplacement Hero

	}
}
