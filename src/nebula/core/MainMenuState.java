package nebula.core;


import java.awt.Toolkit;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

import nebula.core.NebulaGame.State;

public class MainMenuState extends BasicGameState {

	private int stateID;
	private UnicodeFont font;

	private String[] menu = {"Mode Aventure", "Choix Du Jeu", "Retour Au Menu"};
	private int labelSelectionne;

	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateGame)
			throws SlickException {
		// Menu sélectionné
		labelSelectionne = 0;
		//TODO
		font = new UnicodeFont("ressources/font/batmfa.ttf", (int)((Toolkit.getDefaultToolkit().getScreenSize().width/1920.0f) * 44.0f), false, false);
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		font.loadGlyphs();
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateGame, Graphics g)
			throws SlickException {

		int x = gameContainer.getScreenWidth()/3;
		int y = gameContainer.getScreenHeight()/3;
		
		for(int i = 0; i < menu.length; i++) {
			if(i==labelSelectionne) {
				//TODO
				font.drawString(x, y, menu[i], Color.red);
			} else {
				((nebula.core.NebulaGame) stateGame).getUFont().drawString(x, y, menu[i], Color.white);
			}
			y+=50;
		}
	}
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateGame, int arg2)
			throws SlickException {
		Input input = gameContainer.getInput();
		
		
		
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			if(++labelSelectionne >= menu.length) {
				labelSelectionne = 0;
			}
		} else if(input.isKeyPressed(Input.KEY_UP)) {
			if(--labelSelectionne < 0) {
				labelSelectionne = menu.length-1;
			}
		} else if(input.isKeyPressed(Input.KEY_ENTER)) {
			String texte = menu[labelSelectionne];
			int stateChange = State.MAIN_MENU.getValeur();

			if("retour au menu".equalsIgnoreCase(texte))
				;// stateGame.exit();
			else if("mode aventure".equalsIgnoreCase(texte))
				stateChange = State.DEBUT_AVENTURE.getValeur();
			else if("choix du jeu".equalsIgnoreCase(texte)) {
				String[] s = {"BreakOut", "Gravity", "SpaceInvaders", "SpaceShepard", "Retour"};
				menu = s;
				labelSelectionne = 0;
			}
			else if("retour".equalsIgnoreCase(texte)) {
				String[] s = {"Mode Aventure", "Choix Du Jeu", "Retour Au Menu"};
				menu = s;
				labelSelectionne = 0;
			}
			else if("breakout".equalsIgnoreCase(texte))
				stateChange = 10;
			else if("spaceinvaders".equalsIgnoreCase(texte))
				stateChange = State.JEU_SPACEINVADERS.getValeur();
			
			
			// Changement d'état
			stateGame.enterState(stateChange, null, new FadeInTransition(Color.black,1000));
		}		
	}

	@Override
	public int getID() {
		return stateID;
	}

}
