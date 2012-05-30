/**
 * Nebula - Copyright (C) 2012
 * Gweimport nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
he Free Software Foundation, either version 3 of the License, or
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
package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * End menu state
 */
public class EndMenuState extends AbstractMenuState
{
    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Add menu items
        setMenuTitle("Félicitation !");
        addMenuItem("Bravo, tu as fini l'aventure !", null, false);
        addMenuSpaces(1);
        addMenuItem("Score : " + NebulaConfig.getAdventureScore(), null, false);
        addMenuSpaces(2);
        addMenuItem("Continuer", null, true);

        // Music
        initMusic("ressources/sons/common/party.ogg", 0.6f, false);
    }

    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        nebulaGame.initAndEnterState(NebulaState.Credits.id, TransitionType.Fade);
    }

    @Override
    public int getID () { return NebulaState.EndMenu.id; }
}
