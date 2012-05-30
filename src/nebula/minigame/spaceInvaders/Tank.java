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

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Tank
{
	private Image image = null;
	private Image imageInvincibility = null;
	private static Sound sonExp = null;
	private static Sound sonTir = null;
	private float x;
	private float y;
	private int vies;
	private int tirEffectue;

	public Tank(int width, int height) throws SlickException
	{
		image = new Image("ressources/images/spaceInvaders/saucer.png");
		imageInvincibility = new Image("ressources/images/spaceInvaders/saucer-inv.png");
        imageInvincibility.setAlpha(0.95f);
        tirEffectue = 0;
        if (sonExp == null)
        {
            sonExp = new Sound("ressources/sons/spaceInvaders/explosion.ogg");
            sonTir = new Sound("ressources/sons/spaceInvaders/tir.ogg");
        }

        x = width/2 - this.getImage().getWidth()/2;
		y = height/2 - 2 * this.getImage().getHeight() + 10;
		vies = 3;
	}

	public void tirer(Tir tir)
	{
		if(tir.getY() < 0)
		{
			tir.setX(this.getX() + this.getImage().getWidth()/2 - tir.getImage().getWidth()/2);
			tir.setY(this.getY() - tir.getImage().getHeight());
			sonTir.play();
			tirEffectue++;
		}
	}

	public boolean touche(Tir tir)
	{
		return Collision.rectangle(this.getX(),this.getY(), 3*this.getImage().getWidth()/4, 3*this.getImage().getHeight()/4, tir.getX(), tir.getY(), tir.getImage().getWidth(), tir.getImage().getHeight()/3);
	}

	public boolean dead()
	{
		return vies == 0;
	}

	public void kill()
	{
		this.vies = 0;
	}

	public void decrementeVie()
	{
		this.vies--;
	}

	public void addVie()
	{
		this.vies++;
	}

	public Image getImage() {
		return image;
	}

	public Image getImageInv() {
		return imageInvincibility;
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

	public int getVies()
	{
		return vies;
	}

	public Sound getSon()
	{
		return sonExp;
	}

	public int getTirEffectue()
	{
		return tirEffectue;
	}
}
