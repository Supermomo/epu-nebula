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


/**
 * BricksField class
 */
class BricksField
{
    private float x;
    private float y;
    private float w;
    private float h;
    private int r;
    private int c;

    public BricksField (float x, float y, float w, float h, int r, int c)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.r = r;
        this.c = c;
    }

    public float getX ()
    {
        return x;
    }

    public float getY ()
    {
        return y;
    }

    public float getWidth ()
    {
        return w;
    }

    public float getHeight ()
    {
        return h;
    }

    public int getRow ()
    {
        return r;
    }

    public int getColumn ()
    {
        return c;
    }
}
