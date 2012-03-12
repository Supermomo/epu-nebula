package nebula.minigame.cropcircle;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private float x = 400;
	private float y = 300;
	private ArrayList<ArrayList<Point>> loadedTracks;
	private ArrayList<Point> track;
	private Image imgPath;
	private ArrayList<Point> path;

	public SimpleGame() {
		super("Slick2DPath2Glory - SimpleGame");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		track = new ArrayList<Point>();
		try {
			Charger();
			track=loadedTracks.get(loadedTracks.size()-1);
		} catch (Exception e) {
			loadedTracks=new ArrayList<ArrayList<Point>>();
		}
		/*Point p = new Point();
		p.x = 200;
		p.y = 200;
		track.add(p);
		p = new Point();
		p.x = 205;
		p.y = 200;
		track.add(p);
		p = new Point();
		p.x = 210;
		p.y = 200;
		track.add(p);
		p = new Point();
		p.x = 220;
		p.y = 200;
		track.add(p);
		p = new Point();
		p.x = 240;
		p.y = 200;
		track.add(p);
		p = new Point();
		p.x = 260;
		p.y = 200;
		track.add(p);
		p = new Point();
		p.x = 280;
		p.y = 200;
		track.add(p);*/
		path = new ArrayList<Point>();
		imgPath = new Image("assets/cropCircle/braise.png");
		land = new Image("assets/cropCircle/spritBleu.jpg");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		Input input = gc.getInput();

		 //x = input.getMouseX();
		 //y = input.getMouseY();

		if (input.isKeyDown(Input.KEY_SPACE)) {
			if ((path.size() >= 1 && path.get(path.size() - 1) != null && (Math
					.abs(path.get(path.size() - 1).x - x) > 30 || Math.abs(path
					.get(path.size() - 1).y
					- y) > 30))
					|| path.isEmpty()) {

				int index = getIndexOfX((int) x);

				if (index != -1 && Math.abs(track.get(index).y - y) < 20) {
					Point p = new Point();
					p.x = (int) x;
					p.y = (int) y;

					path.add(p);
				}

			}
		}

		
		if (input.isKeyDown(Input.KEY_ENTER)) {
			loadedTracks.add(path);
			try {
				sauvegarder();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			x--;
			x--;
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			x++;
			x++;
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			y--;
			y--;
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			y++;
			y++;
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		land.draw(0, 0, 800, 600);
		gc.getGraphics().setColor(Color.white);
		for (Point p : track) {
			gc.getGraphics().fillOval(p.x, p.y, 2, 2);
		}

		for (Point p : path) {
			imgPath.draw(p.x - 30, p.y - 30);
		}
		gc.getGraphics().setColor(Color.yellow);
		gc.getGraphics().drawLine(x, y, gc.getWidth() / 2, gc.getHeight());

	}

	private int getIndexOfX(int x) {
		for (int i = 0; i < track.size(); i++) {
			if (track.get(i).x == x)
				return i;
		}
		return -1;
	}

	private void sauvegarder() throws ClassNotFoundException {
		String filename = "trackList.data";
		FileOutputStream fis = null;
		ObjectOutputStream oit = null;
		try {
			fis = new FileOutputStream(filename);
			oit = new ObjectOutputStream(fis);
			oit.writeObject(loadedTracks);
			oit.flush();
			oit.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void Charger() throws ClassNotFoundException, IOException, Exception {
		String filename = "trackList.data";
		FileInputStream fos = new FileInputStream(filename);
		ObjectInputStream out = new ObjectInputStream(fos);
		loadedTracks =  (ArrayList<ArrayList<Point>>) out.readObject();
		out.close();
	}

	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new SimpleGame());

		app.setDisplayMode(800, 600, false);
		app.start();
	}
}