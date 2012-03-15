package nebula.minigame.gravity;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

public class BaseJeu extends BasicGame {

	ModeleJeu modeleJeu;
	ControleJeu controleJeu;

	public BaseJeu() {
		super("Gravity");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		modeleJeu = new ModeleJeu(new Player("assets/gravity/hero.png",6,30,30), new BlockMap("assets/gravity/map.tmx"));
		controleJeu = new ControleJeu(modeleJeu);
		container.setVSync(true);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {		
		controleJeu.inputJoueur(container.getInput(), delta);
		modeleJeu.hero.deplace();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		modeleJeu.getMap().getTiledMap().render(0, 0);
		g.drawAnimation(modeleJeu.getHero().getAnimation(), modeleJeu.getHero().getX(), modeleJeu.getHero().getY());
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new BaseJeu());
			app.setDisplayMode(800, 600, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}