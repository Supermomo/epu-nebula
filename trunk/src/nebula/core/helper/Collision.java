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
package nebula.core.helper;

import org.newdawn.slick.geom.Rectangle;


/**
 * Collision helper
 */
public class Collision
{
    /**
     * Check a collision between two rectangles
     */
    public static boolean rectangle (
        float x1, float y1, float w1, float h1,
        float x2, float y2, float w2, float h2
    )
    {
        return (!(y1+h1 < y2 || y1 > y2+h2 || x1+w1 < x2 || x1 > x2+w2));
    }

    /**
     * Check a collision between two rectangles
     */
    public static boolean rectangle (Rectangle r1, Rectangle r2)
    {
        return Collision.rectangle(
            r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight(),
            r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight()
        );
    }

    /**
     * Check a collsion between a point and a rectangle
     */
    public static boolean point (
        float x1, float y1,
        float x2, float y2, float w2, float h2
    )
    {
        return (!(y1 < y2 || y1 > y2+h2 || x1 < x2 || x1 > x2+w2));
    }

    public static boolean right (
        float x1,
        float x2, float w2
    )
    {
        return (x2+w2 < x1);
    }

    public static boolean left (
        float x1, float w1,
        float x2
    )
    {
        return (x1+w1 < x2);
    }

    public static boolean top (
        float y1, float h1,
        float y2
    )
    {
        return (y1+h1 < y2);
    }

    public static boolean bottom (
        float y1,
        float y2, float h2
    )
    {
        return (y2+h2 < y1);
    }
}
