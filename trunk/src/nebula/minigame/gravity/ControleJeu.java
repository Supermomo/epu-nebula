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
		//TODO Ajouter le contrôle du délais entre deux changements de gravité

		// -- Déplacement Hero
		// Déplacement à gauche
		if (input.isKeyDown(Input.KEY_LEFT)) {
			modeleJeu.deplacementGauche(delta);
		}
		// Déplacement à droite
		else if (input.isKeyDown(Input.KEY_RIGHT)) {
			modeleJeu.deplacementDroite(delta);
		}
		/*else if (input.isKeyDown(Input.KEY_UP)) {
			
		}
		else if (input.isKeyDown(Input.KEY_DOWN)) {
			System.out.println(modeleJeu.getHero().getPosition());
		}

		// Courrir
		if(input.isKeyPressed(Input.KEY_LCONTROL)) {
			//TODO Passer en KeyDown
			//modeleJeu.courrir();
		}
		*/
		// Changer la gravité
		if(input.isKeyPressed(Input.KEY_SPACE))
			modeleJeu.inverserGravite();
		else {
			//modeleJeu.deplacementBas(modeleJeu.getGravite()*delta);
			/*		
			 * // Le personnage s'arréte
			 * if(!hero.isStill() && hero.getStill()<35) hero.incStill();
			 * else if(hero.getStill()!=-1) hero.setAnimStill();
			 */
		}
		modeleJeu.appliquerGravite(delta);
		// -- Fin - déplacement Hero

	}
}
