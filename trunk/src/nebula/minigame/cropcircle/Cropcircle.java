package nebula.minigame.cropcircle;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

public class Cropcircle extends BasicGame {

	private Image land = null;
	private float x = 400;
	private float y = 300;
	private ArrayList<ArrayList<Line>> loadedTracks;
	private ArrayList<Line> track;
	private Image imgPath;
	private ArrayList<Vector2f> path;

	public Cropcircle() {
		super("Slick2DPath2Glory - SimpleGame");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		track = new ArrayList<Line>();
		try {
			Charger();
			Random r=new Random();
			track=loadedTracks.get(r.nextInt(loadedTracks.size()));
			
		} catch (Exception e) {
			loadedTracks=new ArrayList<ArrayList<Line>>();
		}

		path = new ArrayList<Vector2f>();
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
			
				Vector2f p = new Vector2f(x,y);
				if (validDistanceFromClosestLine(p)) {
					path.add(p);
				}

			}
		}

		
		if (input.isKeyDown(Input.KEY_ENTER)) {
			ArrayList<Line> sav=new ArrayList<Line>();
			Line l=new Line(250, 50, 250, 150);
			sav.add(l);
			l=new Line(350, 50, 350, 350);
			sav.add(l);
			l=new Line(300, 400, 400, 400);
			sav.add(l);
			l=new Line(200, 300, 300, 400);
			sav.add(l);
			l=new Line(400, 400, 500, 300);
			sav.add(l);
			
			loadedTracks.add(sav);
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
		g.setColor(Color.white);

		for (Line p : track) {
			g.drawLine(p.getX1(),p.getY1(),p.getX2(),p.getY2());
		}

		for (Vector2f v : path) {
			imgPath.draw(v.x - 30, v.y - 30);
		}
		g.setColor(Color.yellow);
		g.drawLine(x, y, gc.getWidth() / 2, gc.getHeight());
		
	}

	private boolean validDistanceFromClosestLine(Vector2f vect){
		int i=0;
		Line line;
		line=track.get(i);
		while(line != null){
			//TODO
			if(Math.abs(line.distance(vect))<10){
				return true;
			}
			i++;
			line=track.get(i);
		}
		return false;
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
		loadedTracks =  (ArrayList<ArrayList<Line>>) out.readObject();
		out.close();
	}

	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new Cropcircle());

		app.setDisplayMode(800, 600, false);
		app.start();
	}
}