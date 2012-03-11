package nebula.game.cropcircle;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SimpleGame extends BasicGame {

	private Image land = null;
	float x = 400;
	float y = 300;
	float scale = 1.0f;
	private Image imgPath;
	private ArrayList<Point> path;

	public SimpleGame() {
		super("Slick2DPath2Glory - SimpleGame");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		path = new ArrayList<Point>();
		imgPath = new Image("assets/Morrowind_Sign-2.png");
		land = new Image("assets/spritBlé.jpg");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_SPACE)) {
			if((path.size()>=1 && path.get(path.size()-1) != null && (Math.abs(path.get(path.size()-1).x-x)>5 
					|| Math.abs(path.get(path.size()-1).y-y)>5)) || path.isEmpty()){
				Point p = new Point();
				p.x = (int) x;
				p.y = (int) y;
				path.add(p);
			}
		}

		if (input.isKeyDown(Input.KEY_Q)) {
			x--;
		}

		if (input.isKeyDown(Input.KEY_D)) {
			x++;
		}

		if (input.isKeyDown(Input.KEY_Z)) {
			y--;
		}
		if (input.isKeyDown(Input.KEY_S)) {
			y++;
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		land.draw(0, 0, 800, 600);
		gc.getGraphics().setColor(Color.yellow);
		gc.getGraphics().drawLine(x, y, gc.getWidth() / 2, gc.getHeight());

		for (Point p : path) {
			imgPath.draw(p.x, p.y);
		}

	}

	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new SimpleGame());

		app.setDisplayMode(800, 600, false);
		app.start();
	}
}