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
package nebula.minigame.boss;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Tir
{

	private Image image = null;
	private Sound son = null;
	private float x = -100;
	private float y = -100;
	private boolean tire;

	public Tir(boolean b) throws SlickException
	{
		if(b)
		{
			image = new Image("ressources/images/boss/ball.png");
			son = new Sound("ressources/sons/boss/tirEnnemi.ogg");
			y = -100;
		}
		else
		{
			image = new Image("ressources/images/boss/tir.png");
			son = new Sound("ressources/sons/boss/tir.ogg");
			y = -100;
		}

	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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

	public void setTire(boolean a)
	{
		this.tire = a;
	}

	public boolean getTire()
	{
		return this.tire;
	}

	public Sound getSon()
	{
		return this.son;
	}

}
