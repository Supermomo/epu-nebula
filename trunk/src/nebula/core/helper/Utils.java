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

import nebula.core.state.AbstractMinigameState.Difficulty;


/**
 * Util helper
 */
public class Utils
{
    /**
     * Convert time in seconds to string
     */
    public static String secondsToString (int sec)
    {
        return Integer.toString(sec/60) +
               " : " +
               (sec % 60 < 10 ? "0" : "") +
               Integer.toString(sec % 60);
    }

    /**
     * Returns the score in the nebula default ranges
     */
    public static int checkScoreRange (int score, Difficulty difficulty)
    {
        if (Difficulty.Easy.equals(difficulty))
        {
            score = Math.max(1000, score);
            score = Math.min(score, 2000);
        }
        else if (Difficulty.Medium.equals(difficulty))
        {
            score = Math.max(2000, score);
            score = Math.min(score, 3000);
        }
        else if (Difficulty.Hard.equals(difficulty))
        {
            score = Math.max(3000, score);
            score = Math.min(score, 4000);
        }
        else if (Difficulty.Insane.equals(difficulty))
        {
            score = Math.max(4000, score);
            score = Math.min(score, 5000);
        }

        return score;
    }
}
