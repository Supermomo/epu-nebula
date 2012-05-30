/**
 * Nebula - Copyright (C) 2012
 * Gweimport java.util.List;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
ense, or
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

import java.util.List;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.config.NebulaConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Load player menu
 */
public class PlayerSelectMenuState extends AbstractMenuState
{
    private List<String> players;
    private static Sound sndSelect;

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // Sounds
        if (sndSelect == null)
            sndSelect = new Sound(sndPath + "selectplayer.ogg");

        // Get players
        players = NebulaConfig.getPlayers();

        // Add menu items
        setMenuTitle("Choix du joueur");

        for (String p : players)
            addMenuItem(p, null, true);

        addMenuSpaces(1);
        addMenuItem("Retour", sndPath + "cancel.ogg", true);
    }


    @Override
    protected void indexSelectedEvent (int index, StateBasedGame game)
    {
        if (index == -1 || index == players.size())
            nebulaGame.enterState(NebulaState.PlayerMenu.id);
        else if (0 <= index && index < players.size())
        {
            // Load game
            nebulaGame.loadPlayer(players.get(index));
            nebulaGame.initAndEnterState(NebulaState.MainMenu.id, TransitionType.ShortFade);
        }
    }


    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);
        sndSelect.play();
    }


    @Override
    public void leave (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.leave(gc, game);
        sndSelect.stop();
    }


	@Override
	public int getID() { return NebulaState.PlayerSelectMenu.id; }
}
