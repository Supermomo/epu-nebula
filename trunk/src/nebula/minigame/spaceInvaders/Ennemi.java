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
package nebula.minigame.spaceInvaders;

import nebula.core.helper.Collision;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Ennemi
{
	private static Image image = null;
	private float x = 0;
	private float y = 0;
	private int pts = 0;
	private static Sound sonExp = null;
	private static Sound[] sonTir = null;


	public Ennemi(float x, float y) throws SlickException
	{
		if(image == null)
			image = new Image("ressources/images/spaceInvaders/ship.png");
		if(sonExp == null)
		{
			sonExp = new Sound("ressources/sons/spaceInvaders/explosion.ogg");

			sonTir = new Sound[4];
			for (int i = 0; i < sonTir.length; i++)
			    sonTir[i] = new Sound("ressources/sons/spaceInvaders/tirEnnemi.ogg");
		}

		this.x = x;
		this.y = y;
		this.pts = 100;
	}

	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX(),this.getY(), 3*this.getImage().getWidth()/4, 3*this.getImage().getHeight()/4, tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/3);
	}

	public void tirer(Tir tir, GameContainer gc)
	{
		if(tir.getY() > gc.getHeight())
		{
			tir.setX(this.getX());
			tir.setY(this.getY() + tir.getImage().getHeight());

			// Play available sound
			for (Sound s : sonTir)
			{
			    if (!s.playing())
		        {
			        s.play();
			        break;
		        }
			}
		}
	}

	public Sound getSon()
	{
		return sonExp;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		Ennemi.image = image;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getPts() {
		return pts;
	}


}
