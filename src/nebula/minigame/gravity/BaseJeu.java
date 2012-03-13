package nebula.minigame.gravity;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

public class BaseJeu extends BasicGame {

	Player hero;
	BlockMap map;

	public BaseJeu() {
		super("Gravity");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		hero = new Player("assets/gravity/hero.png",6,30,30);
		map = new BlockMap("assets/gravity/map.tmx");
		container.setVSync(true);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// -- Debut - déplacement Hero
		float deplacement = hero.getVitesse()*delta;
		// Déplacement à gauche
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			if(	!map.deplacementBloquant(
					((Float) (hero.getX()-deplacement)).intValue(), // Valeur modifiée
					((Float) (hero.getY()+1)).intValue() )
					&&
					!map.deplacementBloquant(
							((Float) (hero.getX()-deplacement)).intValue(), // Valeur modifiée
							((Float) (hero.getY()+14)).intValue() ))
			{
				hero.moveX(-1*deplacement);
				if(hero.isStill()) hero.setAnimGauche();
			}
		}
		// Déplacement à droite
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			if(!map.deplacementBloquant(
					((Float) (hero.getX()+15+deplacement)).intValue(), // Valeur modifiée
					((Float) (hero.getY()+1)).intValue() )
					&&
					!map.deplacementBloquant(
							((Float) (hero.getX()+15+deplacement)).intValue(), // Valeur modifiée
							((Float) (hero.getY()+14)).intValue() ))
			{
				hero.moveX(+1*deplacement);
				if(hero.isStill()) hero.setAnimDroite();
			}
		}
		// Déplacement en haut
		if (container.getInput().isKeyDown(Input.KEY_UP)) {
			if(!map.deplacementBloquant(
					((Float) (hero.getX()+1)).intValue(),
					((Float) (hero.getY()-deplacement)).intValue() )
					&&
					!map.deplacementBloquant(
							((Float) (hero.getX()+14)).intValue(),
							((Float) (hero.getY()-deplacement)).intValue() ))
				hero.moveY(-1*deplacement);
		}
		// Déplacement en bas
		if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			if(!map.deplacementBloquant(
					((Float) (hero.getX()+1)).intValue(),
					((Float) (hero.getY()+15+deplacement)).intValue() )
					&&
					!map.deplacementBloquant(
							((Float) (hero.getX()+14)).intValue(),
							((Float) (hero.getY()+15+deplacement)).intValue() ))
				hero.moveY(+1*deplacement);
		}
		else {
			// Le personnage s'arrête
			if(!hero.isStill() && hero.getStill()<35) hero.incStill();
			else if(hero.getStill()!=-1) hero.setAnimStill();
		}
		// -- Fin - déplacement Hero
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.getTiledMap().render(0, 0);
		g.drawAnimation(hero.getAnimation(), hero.getX(), hero.getY());
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