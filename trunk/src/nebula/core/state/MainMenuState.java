package nebula.core.state;

import java.awt.Toolkit;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

import nebula.core.NebulaGame;
import nebula.core.NebulaGame.StateID;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.playerAndScore.Player;


public class MainMenuState extends BasicGameState
{
	private Font font;

	private String[] menu = {"Mode Aventure", "Choix Du Jeu","Scores du jeu", "Retour Au Menu"};
	private int labelSelectionne;

	//TODO méthode d'acquisition du nom ?
	private String playerName="Gwenn";

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateGame)
			throws SlickException
	{
		// Menu sélectionné
		labelSelectionne = 0;

		font = NebulaFont.getFont(FontName.Batmfa, (int)((Toolkit.getDefaultToolkit().getScreenSize().width/1920.0f) * 44.0f));
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateGame, Graphics g)
			throws SlickException {

		int x = gameContainer.getScreenWidth()/3;
		int y = gameContainer.getScreenHeight()/3;
		
		for(int i = 0; i < menu.length; i++) {
			if(i==labelSelectionne) {
				font.drawString(x, y, menu[i], Color.red);
			} else {
				font.drawString(x, y, menu[i], Color.white);
			}
			y+=50;
		}
	}
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateGame, int arg2)
			throws SlickException {
		Input input = gameContainer.getInput();
		
		
		
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			if(++labelSelectionne >= menu.length || menu[labelSelectionne].startsWith(playerName)) {
				labelSelectionne = 0;
			}
		} else if(input.isKeyPressed(Input.KEY_UP)) {
			if(--labelSelectionne < 0) {
				labelSelectionne = menu.length-1;
				if(menu[labelSelectionne].startsWith(playerName)){
					labelSelectionne = 0;
				}
			}


		} else if(input.isKeyPressed(Input.KEY_ENTER)) {
			String texte = menu[labelSelectionne];
			int stateChange = StateID.MainMenu.value;

			if("retour au menu".equalsIgnoreCase(texte))
				;// stateGame.exit();
			else if("mode aventure".equalsIgnoreCase(texte)){
				stateChange = StateID.StartAventure.value;
				NebulaGame.isScenario=true;
			}
			else if("choix du jeu".equalsIgnoreCase(texte)) {
				String[] s = {"Breakout", "Asteroid", "Gravity", "SpaceInvaders", "SpaceShepard", "Retour"};
				menu = s;
				labelSelectionne = 0;
				NebulaGame.isScenario=false;
			}
			else if("retour".equalsIgnoreCase(texte)) {
				String[] s = {"Mode Aventure", "Choix Du Jeu","Scores du jeu", "Retour Au Menu"};
				menu = s;
				labelSelectionne = 0;
			}
			// Si on a selectionné un jeu
			else if("breakout".equalsIgnoreCase(texte))
				stateChange = StateID.Breakout.value;
			else if("asteroid".equalsIgnoreCase(texte))
                stateChange = StateID.Asteroid.value;
			else if("spaceinvaders".equalsIgnoreCase(texte))
				stateChange = StateID.SpaceInvaders.value;
			else if("gravity".equalsIgnoreCase(texte))
				stateChange = StateID.Gravity.value;
			else if("scores du jeu".equalsIgnoreCase(texte)){
				String[] s={"Retour",Player.load(playerName).toString()};
				menu=s;
				labelSelectionne = 0;
			}

			//TODO empecher le surlignage du score
			
			// Changement d'état
			stateGame.enterState(stateChange, null, new FadeInTransition(Color.black,1000));
		}
	}

	@Override
	public int getID () {
		return StateID.MainMenu.value;
	}
}
