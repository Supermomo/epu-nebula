package nebula.core;

import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import nebula.core.intro.Bougibouga;
import nebula.core.intro.FinInvaders;
import nebula.core.intro.Intro2Jeu;
import nebula.core.intro.Intro3Jeu;
import nebula.core.intro.IntroJeu;
import nebula.core.intro.Jubba;
import nebula.minigame.breakout.Breakout;
import nebula.minigame.spaceInvaders.SpaceInvaders;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.EffectUtil;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

public class NebulaGame extends StateBasedGame {

	public final static String fontPath = "ressources/font/batmfa.ttf";
	private UnicodeFont uFont;
	
	enum State {
		MAIN_MENU(0),
		OPTION_MENU(1),
		JEU_GRAVITY(10);
		
		private int valeur;
		
		private State(int n) {
			valeur = n;
		}
		
		public int getValeur() { return valeur; }
	}
	
	/**
	 * Constructeur du jeu.
	 * Definit les différents états (menus / jeux) disponnibles
	 * @throws SlickException 
	 */
	public NebulaGame() throws SlickException {
		super("Nebula");
		
		// Ajout des GameStates
		this.addState((new MainMenuState(State.MAIN_MENU.getValeur())));
		this.addState(new OptionMenuState(State.OPTION_MENU.getValeur()));
		this.addState(new IntroJeu());
		this.addState(new Intro2Jeu());
		this.addState(new Intro3Jeu());
		this.addState(new SpaceInvaders(8));
		this.addState(new FinInvaders());
		this.addState(new Bougibouga());
		this.addState(new Jubba());
		this.addState(new Breakout());
		//this.addState(new nebula.minigame.gravity.Gravity(9));
		
		uFont = new UnicodeFont(fontPath, (int)((Toolkit.getDefaultToolkit().getScreenSize().width/1920.0f) * 44.0f), false, false);
		uFont.addAsciiGlyphs();
		uFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
		
		// Selection de l'état de départ
		this.enterState(State.MAIN_MENU.getValeur());
	}

	/**
	 * 
	 */
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(State.MAIN_MENU.getValeur()).init(gameContainer, this);
        this.getState(State.OPTION_MENU.getValeur()).init(gameContainer, this);
	}

	/**
	 * Méthode principale lancée au début du jeu
	 * @param args
	 * @throws SlickException
	 */
	
	public void next(int currentState, int typeFondu)
	{
		switch (typeFondu) 
		{
			case 0:
				enterState(++currentState, new FadeOutTransition(Color.black, 3000), new FadeInTransition(Color.black,3000));
			break;

			case 1:
				enterState(++currentState, null, new HorizontalSplitTransition(Color.black));
			break;
			
			case 2:
				enterState(++currentState, null, new VerticalSplitTransition(Color.black));
			break;
		
		default:
			enterState(++currentState, new FadeOutTransition(Color.black, 3000), new FadeInTransition(Color.black,3000));
		break;
		}
		
	}
	
	public void next(int currentState)
	{
		enterState(++currentState, new FadeOutTransition(Color.black, 3000), new FadeInTransition(Color.black,3000));
	}

	public UnicodeFont getUFont()
	{
		return uFont;
	}
}
