package nebula.minigame.gravity;


import java.awt.Toolkit;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

public class BaseJeu extends BasicGame {

	private final static String dossierData = "assets/images/gravity/";
	private ModeleJeu modeleJeu;
	private ControleJeu controleJeu;
	private Image victoire, defaite, coeur;

	public BaseJeu() {
		super("Gravity");
	}

	/**
	 * Statut d'initialisation du jeu
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		try {
			// Map 1 ou map 2
			modeleJeu = new ModeleJeu(new Player(dossierData+"heroSet.png",200,300), new BlockMap(dossierData+"2.tmx"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		controleJeu = new ControleJeu(modeleJeu);
		victoire = new Image(dossierData+"victoire.png");
		defaite = new Image(dossierData+"defaite.png");
		coeur = new Image(dossierData+"coeur.png");
		container.setVSync(true);
	}

	/**
	 * Boucle principale qui itère l'avancement du jeu
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {	
		controleJeu.inputJoueur(container.getInput(), delta);
		modeleJeu.getHero().incStill();
		// if(modeleJeu.finJeu()) stop();
	}

	/**
	 * Méthode appellée à chaque fois que l'on veut dessiner l'état du jeu à l'écran
	 */
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		modeleJeu.getMap().getTiledMap().render(0, 0);
		g.drawAnimation(modeleJeu.getHero().getAnimation(), modeleJeu.getHero().getX(), modeleJeu.getHero().getY());
    	
		if(modeleJeu.getFin())
			if(modeleJeu.getVictoire())
				victoire.draw(100, 250);
			else if(modeleJeu.getDefaite())
				defaite.draw(100,250);

    	for(int i = 0; i < modeleJeu.getHero().getNbrVies(); i++) {
    		coeur.draw(10 + i * coeur.getWidth(), container.getHeight() - coeur.getHeight());
    	}
	}

	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new BaseJeu());
			app.setDisplayMode(Toolkit.getDefaultToolkit().getScreenSize().width,
	 				Toolkit.getDefaultToolkit().getScreenSize().height, true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		
	}
}