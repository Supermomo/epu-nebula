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
 * Speed vector class
 */
class SpeedVector
{
    private float x;
    private float y;
    private float speedCounter;

    public SpeedVector ()
    {
        reset();
    }

    public void reset ()
    {
        this.x = 0.0f;
        this.y = 0.0f;
        this.speedCounter = 0.0f;
    }

    public void invertX ()
    {
        x = -x;
    }

    public void invertY ()
    {
        y = -y;
    }

    public void setAngle (float angle)
    {
        float speed = getSpeed();
        x = (float)Math.cos(angle * (Math.PI / 180.0)) * speed;
        y = (float)Math.sin(angle * (Math.PI / 180.0)) * speed;
    }

    public float getAngle ()
    {
        return (float)(Math.atan2(y, x) * (180.0 / Math.PI));
    }

    public void increaseSpeed (float i)
    {
        speedCounter += i;
        while (speedCounter >= 100.0f)
        {
            x *= 1.01f;
            y *= 1.01f;
            speedCounter -= 100.0f;
        }
    }

    public float getSpeed ()
    {
        return (float)Math.sqrt(x*x + y*y);
    }

    public float getX ()
    {
        return x;
    }

    public void setX (float x)
    {
        this.x = x;
    }

    public float getY ()
    {
        return y;
    }

    public void setY (float y)
    {
        this.y = y;
    }
}
