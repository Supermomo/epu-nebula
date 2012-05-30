/**
 * Nebula - Copyright (C) 2012
 * Gwenn Aubert, Thomas Di'Meco, Matthieu Maugard, Gaspard Perrot
 *
 * This file is part of project 'Nebula'
 *
 * 'Nebula' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * 'Nebula' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Nebula'. If not, see <http://www.gnu.org/licenses/>.
 */
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
