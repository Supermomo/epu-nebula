package nebula.minigame.gravity;

import org.newdawn.slick.Input;

public class ControleJeu {
	
	ModeleJeu modeleJeu;
	
	public ControleJeu(ModeleJeu modeleJeu) {
		this.modeleJeu = modeleJeu;
	}
	
	public void inputJoueur(Input input, int delta) {

		// -- D�placement Hero
		// D�placement � gauche
		if (input.isKeyDown(Input.KEY_LEFT)) {
			modeleJeu.deplacementGauche(delta);
		}
		// D�placement � droite
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			modeleJeu.deplacementDroite(delta);
		}
		// D�placement en haut
		if (input.isKeyDown(Input.KEY_UP)) {
			modeleJeu.deplacementHaut(delta);
		}
		// D�placement en bas
		if (input.isKeyDown(Input.KEY_DOWN)) {
			modeleJeu.deplacementBas(delta);
		}
		// Courrir
		if(input.isKeyDown(Input.KEY_LSHIFT))
			modeleJeu.courrir();
		// Changer la gravit�
		if(input.isKeyPressed(Input.KEY_SPACE))
			modeleJeu.inverserGravite();
		else {
	/*		
	 * // Le personnage s'arr�te
	 * if(!hero.isStill() && hero.getStill()<35) hero.incStill();
	 * else if(hero.getStill()!=-1) hero.setAnimStill();
	 */
		}
		// -- Fin - d�placement Hero

	}
}
