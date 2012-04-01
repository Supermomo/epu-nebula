package nebula.minigame.gravity;

import org.newdawn.slick.Input;

public class ControleJeu {

	ModeleJeu modeleJeu;

	public ControleJeu(ModeleJeu modeleJeu) {
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
		else if (input.isKeyDown(Input.KEY_UP)) {
			/*
			 * AFFICHAGE DES ÉLÉMENTS DE CONTRÔLE
			 */
			BlockType[][] blocks = modeleJeu.getMap().getBlocks();
			for(int i=blocks.length-1;i>=0;i--) {
				for(int j=0;j<blocks[i].length;j++) {
					BlockType b = blocks[i][j];
					if(b!=null) System.out.print(b.toString()+"\t");
					else System.out.print("____\t");
				}
				System.out.println();
			}
			System.out.println();
		}

		// Courrir
		if(input.isKeyDown(Input.KEY_LSHIFT))
			modeleJeu.courrir();
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
