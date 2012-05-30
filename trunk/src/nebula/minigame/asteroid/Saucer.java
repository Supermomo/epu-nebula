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
package nebula.minigame.asteroid;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;


/**
 * Saucer class
 */
class Saucer
{
    static final float w = 128;
    static final float h = 93;
    static final float speed = 0.3f;
    static final float collideOffset = 16.0f;

    private float x;
    private float y;
    private Rectangle limits;
    private static Image image, imageInvincibility;

    public Saucer (Rectangle limits) throws SlickException
    {
        this.limits = limits;
        resetPosition();

        // Set saucer images first time
        if (image == null || imageInvincibility == null)
        {
            image = new Image(AsteroidGame.imgPath + "saucer.png");
            imageInvincibility = new Image(AsteroidGame.imgPath + "saucer-inv.png");
            imageInvincibility.setAlpha(0.95f);
        }
    }

    public void goRight (float n)
    {
        x += n;

        float limit = limits.getX() + limits.getWidth();
        if (x + w > limit) x = limit - w;
    }

    public void goLeft (float n)
    {
        x -= n;

        float limit = limits.getX();
        if (x < limit) x = limit;
    }

    public void goUp (float n)
    {
        y -= n;

        float limit = limits.getY();
        if (y < limit) y = limit;
    }

    public void goDown (float n)
    {
        y += n;

        float limit = limits.getY() + limits.getHeight();
        if (y + h > limit) y = limit - h;
    }

    public void resetPosition ()
    {
        x = limits.getX() + limits.getWidth()/2 - w/2;
        y = limits.getY() + limits.getHeight()/2 - h/2;
    }

    public Ellipse getCollideZone ()
    {
        return new Ellipse(
            x+Saucer.w/2,
            y+Saucer.h/2,
            Saucer.w/2-Saucer.collideOffset,
            Saucer.h/2-Saucer.collideOffset
        );
    }

    public void draw (boolean invincibility)
    {
        if (invincibility)
            imageInvincibility.draw(x, y, w, h);
        else
            image.draw(x, y, w, h);
    }

    public void setX (float x)
    {
        this.x = x;
    }

    public float getX ()
    {
        return x;
    }

    public void setY (float y)
    {
        this.y = y;
    }

    public float getY ()
    {
        return y;
    }
}
