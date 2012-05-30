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
package nebula.minigame.breakout;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * Ball class
 */
class Ball
{
    static final float w = 48;
    static final float h = 48;

    private float x;
    private float y;
    private float xprev;
    private float yprev;
    private static Image image;

    public Ball () throws SlickException
    {
        // Set ball image first time
        if (image == null)
            image = new Image(BreakoutGame.imgPath + "ball.png");
    }

    public void draw ()
    {
        image.draw(x, y, w, h);
    }

    public void goPrevPosition ()
    {
        x = xprev;
        y = yprev;
    }

    public void setX (float x)
    {
        xprev = this.x;
        this.x = x;
    }

    public float getX ()
    {
        return x;
    }

    public void setY (float y)
    {
        yprev = this.y;
        this.y = y;
    }

    public float getY ()
    {
        return y;
    }
}
